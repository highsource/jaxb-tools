package org.jvnet.jaxb.maven.resolver.tools;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb.maven.XJCVersion;
import org.jvnet.jaxb.maven.plugin.logging.NullLog;
import org.jvnet.jaxb.maven.util.StringUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReResolvingEntityResolverWrapper implements EntityResolver {

	private final EntityResolver entityResolver;
    private final Log log;
    private final boolean disableSystemIdResolution;

	public ReResolvingEntityResolverWrapper(EntityResolver entityResolver, Log log, boolean disableSystemIdResolution, XJCVersion version) {
		if (entityResolver == null) {
			throw new IllegalArgumentException("Provided entity resolver must not be null.");
		}
		this.entityResolver = entityResolver;
		this.log = Optional.ofNullable(log).orElse(NullLog.INSTANCE);
        this.disableSystemIdResolution = computeDisableSystemIdResolution(disableSystemIdResolution, version);
	}

    private boolean computeDisableSystemIdResolution(boolean disableSystemIdResolution, XJCVersion version) {
        boolean finalDisableSystemIdResolution = disableSystemIdResolution;
        boolean versionCheck = false;
        if (version.isKnown()) {
            versionCheck = true;
            if (version.gte(4, 0, 4) && !disableSystemIdResolution) {
                log.info("ReResolvingEntityResolverWrapper : systemIdResolution fix has been auto-disabled (current running XJC is " + version.getRaw() + ").");
                finalDisableSystemIdResolution = true;
            } else if (version.gte(4, 0, 0) && version.lt(4, 0, 4) && disableSystemIdResolution) {
                log.info("ReResolvingEntityResolverWrapper : systemIdResolution fix has been auto-enabled (current running XJC is " + version.getRaw() + ").");
                finalDisableSystemIdResolution = false;
            }
        }
        if (finalDisableSystemIdResolution) {
            if (!versionCheck) {
                log.warn("ReResolvingEntityResolverWrapper : systemIdResolution fix is disable, you may have problems with schema resolution.");
            } else {
                log.debug("ReResolvingEntityResolverWrapper : systemIdResolution fix is disabled");
            }
        } else {
            log.debug("ReResolvingEntityResolverWrapper : systemIdResolution fix is enabled");
        }
        return finalDisableSystemIdResolution;
    }

    @Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		log.debug(MessageFormat.format("ReResolvingEntityResolverWrapper : Resolving publicId [{0}], systemId [{1}].", publicId, systemId));
		final InputSource resolvedInputSource = this.entityResolver.resolveEntity(publicId, systemId);
		if (resolvedInputSource == null) {
			log.debug("ReResolvingEntityResolverWrapper : Resolution result is null.");
			return null;
		} else {
			log.debug(MessageFormat.format("ReResolvingEntityResolverWrapper : Resolved to publicId [{0}], systemId [{1}].", resolvedInputSource.getPublicId(), resolvedInputSource.getSystemId()));
			final String pId = !StringUtils.isEmpty(publicId) ? publicId : resolvedInputSource.getPublicId();
			final String sId = !StringUtils.isEmpty(systemId) ? systemId : resolvedInputSource.getSystemId();
            log.debug(MessageFormat.format("ReResolvingEntityResolverWrapper : Final Resolved to publicId [{0}], systemId [{1}].", pId, sId));
            return new ReResolvingInputSourceWrapper(this.entityResolver, this.disableSystemIdResolution, this.log, resolvedInputSource, pId, sId);
		}
	}
}
