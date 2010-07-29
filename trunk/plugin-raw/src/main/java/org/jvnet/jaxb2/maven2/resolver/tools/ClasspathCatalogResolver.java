package org.jvnet.jaxb2.maven2.resolver.tools;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

public class ClasspathCatalogResolver extends
		com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver {

	public static final String URI_SCHEME_CLASSPATH = "classpath";

	@Override
	public String getResolvedEntity(String publicId, String systemId) {
		final String result = super.getResolvedEntity(publicId, systemId);

		if (result == null) {
			System.err.println(MessageFormat.format(
					"Could not resolve publicId [{0}], systemId [{1}]",
					publicId, systemId));
			return null;
		}

		try {
			final URI uri = new URI(result);
			if (URI_SCHEME_CLASSPATH.equals(uri.getScheme())) {
				final String schemeSpecificPart = uri.getSchemeSpecificPart();

				final URL resource = Thread.currentThread()
						.getContextClassLoader()
						.getResource(schemeSpecificPart);
				if (resource == null) {
					return null;
				} else {
					return resource.toString();
				}
			} else {
				return result;
			}
		} catch (URISyntaxException urisex) {

			return result;
		}
	}
}
