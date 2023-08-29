package org.jvnet.jaxb.maven.resolver.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.maven.plugin.logging.Log;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReResolvingInputSourceWrapper extends InputSource {

    private final EntityResolver entityResolver;

    private final boolean disableSystemIdResolution;

    private final Log log;

    private final InputSource inputSource;

    public ReResolvingInputSourceWrapper(EntityResolver entityResolver, boolean disableSystemIdResolution,
                                         Log log, InputSource inputSource, String publicId, String systemId) {
        this.entityResolver = entityResolver;
        this.disableSystemIdResolution = disableSystemIdResolution;
        this.log = log;
        this.inputSource = inputSource;
        this.setPublicId(publicId);
        this.setSystemId(systemId);
    }

    @Override
    public String getSystemId() {
        String systemId = super.getSystemId();
        if (!disableSystemIdResolution) {
            String callerClassName = getCallerClassName();
            if (callerClassName.endsWith("domforest")) {
                log.debug("ReResolvingInputSourceWrapper : Handling DOMForest xjc 2.3.4+ change");
                // DomForest checks now if file in initial systemId exists and override it if it doesnt
                //  --> This breaks CatalogResolution (JT-306)
                // Do the check here, and if file doesn't exist, return the inputSource.systemId instead,
                //  which is the resolved systemId of the resource
                try {
                    URI uri = new URI(systemId);
                    if ("file".equals(uri.getScheme())) {
                        if (!Files.exists(Paths.get(uri))) {
                            log.debug("ReResolvingInputSourceWrapper : Initial systemId "
                                + systemId + " is file, and does not exist"
                                + ", returning inputSource.getSystemId() as systemId (" + inputSource.getSystemId() + ")");
                            systemId = inputSource.getSystemId();
                        }
                    }
                } catch (URISyntaxException ex) {
                    //ignore, let it be handled by parser as is
                }
            }
        }
        return systemId;
    }

    /**
     * Returns the caller className, in lowercase form
     * @return the caller className of this method
     */
    private static String getCallerClassName() {
        StackTraceElement[] trace = new Exception().getStackTrace();
        // trace[0] === this method
        // trace[1] === this class, getSystemId
        // trace[2] === caller class name
        return Optional.ofNullable(trace[2].getClassName()).map(c -> c.toLowerCase()).orElse("");
    }

    @Override
	public Reader getCharacterStream() {
		final Reader originalReader = inputSource.getCharacterStream();
		if (originalReader == null) {
			return null;
		} else {
			Reader resolvedEntityReader = getResolvedEntity();
			if (resolvedEntityReader != null) {
				return resolvedEntityReader;
			} else {
				return originalReader;
			}
		}
	}

	private Reader getResolvedEntity() {
		try {
			InputSource resolvedEntity = this.entityResolver.resolveEntity(
					getPublicId(), super.getSystemId());
			if (resolvedEntity == null) {
				return null;
			} else {
				return resolvedEntity.getCharacterStream();
			}
		} catch (IOException ioex) {
			return null;
		} catch (SAXException saxex) {
			return null;
		}
	}

	@Override
	public void setCharacterStream(Reader characterStream) {
	}

	@Override
	public InputStream getByteStream() {
		final InputStream originalInputStream = inputSource.getByteStream();
		if (originalInputStream == null) {
			return null;
		} else {
			try {
				InputSource resolvedEntity = this.entityResolver.resolveEntity(
						getPublicId(), super.getSystemId());
				if (resolvedEntity != null) {
					return resolvedEntity.getByteStream();
				} else {
					return originalInputStream;
				}
			} catch (IOException ioex) {
				return originalInputStream;
			} catch (SAXException saxex) {
				return originalInputStream;
			}
		}
	}

	@Override
	public void setByteStream(InputStream byteStream) {
	}
}
