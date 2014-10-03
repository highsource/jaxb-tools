package org.jvnet.jaxb2.maven2.resolver.tools;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

import org.jvnet.jaxb2.maven2.DependencyResource;
import org.jvnet.jaxb2.maven2.DependencyResourceResolver;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;

public class MavenCatalogResolver extends
		com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver {

	public static final String URI_SCHEME_MAVEN = "maven";
	private final DependencyResourceResolver dependencyResourceResolver;
	private final CatalogManager catalogManager;

	public MavenCatalogResolver(CatalogManager catalogManager,
			DependencyResourceResolver dependencyResourceResolver) {
		super(catalogManager);
		this.catalogManager = catalogManager;
		if (dependencyResourceResolver == null) {
			throw new IllegalArgumentException(
					"Dependency resource resolver must not be null.");
		}
		this.dependencyResourceResolver = dependencyResourceResolver;
	}

	@Override
	public String getResolvedEntity(String publicId, String systemId) {
//		System.out.println("Using the catalog [" + getCatalog() + "].");
//		System.out.println(MessageFormat.format("Resolving publicId [{0}], systemId [{1}].",
//				publicId, systemId));
		String result = super.getResolvedEntity(publicId, systemId);
//		System.out.println(MessageFormat.format("Super resolution result is [{0}].",
//				result));
		if (result == null) {
			if (systemId != null)
			{
				result = systemId;
			}
			else
			{
//				System.out.println("0) Returning null.");
				return null;
			}
		}

		try {
			final URI uri = new URI(result);
			if (URI_SCHEME_MAVEN.equals(uri.getScheme())) {
				final String schemeSpecificPart = uri.getSchemeSpecificPart();
				try {
					final DependencyResource dependencyResource = DependencyResource
							.valueOf(schemeSpecificPart);
					try {
						final URL url = dependencyResourceResolver
								.resolveDependencyResource(dependencyResource);
						String resolved = url.toString();
//						System.out.println(MessageFormat.format("1) Returning [{0}].", resolved));
						return resolved;
					} catch (Exception ex) {
						catalogManager.debug.message(1, MessageFormat.format(
								"Error resolving dependency resource [{0}].",
								dependencyResource));
					}

				} catch (IllegalArgumentException iaex) {
					catalogManager.debug.message(1, MessageFormat.format(
							"Error parsing dependency descriptor [{0}].",
							schemeSpecificPart));

				}
//				System.out.println("2) Returning null.");
				return null;
			} else {
//				System.out.println(MessageFormat.format("3) Returning [{0}].", result));
				return result;
			}
		} catch (URISyntaxException urisex) {
//			System.out.println(MessageFormat.format("4) Returning [{0}].", result));
			return result;
		}
	}

}
