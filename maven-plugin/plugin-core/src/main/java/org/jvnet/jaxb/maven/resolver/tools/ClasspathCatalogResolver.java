package org.jvnet.jaxb.maven.resolver.tools;

import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb.maven.plugin.logging.NullLog;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

public class ClasspathCatalogResolver extends
		org.apache.xml.resolver.tools.CatalogResolver implements LoggingCatalogResolver {

	public static final String URI_SCHEME_CLASSPATH = "classpath";
	private Log log;

	public ClasspathCatalogResolver() {
		super();
		this.log = NullLog.INSTANCE;
	}

	@Override
	public String getResolvedEntity(String publicId, String systemId) {

		log.debug( "ClasspathCatalogResolver : Resolving [" + publicId + "], [" + systemId + "].");
		final String result = super.getResolvedEntity(publicId, systemId);
		log.debug("ClasspathCatalogResolver : Resolved to [" + result+ "].");

		if (result == null) {
			log.info(MessageFormat.format(
					"ClasspathCatalogResolver : Could not resolve publicId [{0}], systemId [{1}]",
					publicId, systemId));
			return null;
		}

		try {
			final URI uri = new URI(result);
			if (URI_SCHEME_CLASSPATH.equals(uri.getScheme())) {
				final String schemeSpecificPart = uri.getSchemeSpecificPart();
				log.debug("ClasspathCatalogResolver : Resolve [" + schemeSpecificPart + "].");

				final URL resource = Thread.currentThread()
						.getContextClassLoader()
						.getResource(schemeSpecificPart);
				if (resource == null) {
					log.debug("ClasspathCatalogResolver : Returning [" + null + "].");
					return null;
				} else {
					log.debug("ClasspathCatalogResolver : Returning to [" + resource.toString() + "].");
					return resource.toString();
				}
			} else {
				log.debug("ClasspathCatalogResolver : Returning to [" + result+ "].");
				return result;
			}
		} catch (URISyntaxException urisex) {
			log.debug("ClasspathCatalogResolver : Returning to [" + result+ "].");
			return result;
		}
	}

	public void setLog(Log log) {
		this.log = log;
	}
}
