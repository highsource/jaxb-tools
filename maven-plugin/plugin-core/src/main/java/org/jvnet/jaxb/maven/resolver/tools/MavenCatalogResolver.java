package org.jvnet.jaxb.maven.resolver.tools;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb.maven.DependencyResource;
import org.jvnet.jaxb.maven.DependencyResourceResolver;
import org.jvnet.jaxb.maven.plugin.logging.NullLog;

import org.apache.xml.resolver.CatalogManager;

public class MavenCatalogResolver extends
		org.apache.xml.resolver.tools.CatalogResolver {

	public static final String URI_SCHEME_MAVEN = "maven";
	private final DependencyResourceResolver dependencyResourceResolver;
	private final Log log;

	public MavenCatalogResolver(CatalogManager catalogManager,
			DependencyResourceResolver dependencyResourceResolver) {
		this(catalogManager, dependencyResourceResolver, NullLog.INSTANCE);
	}

	public MavenCatalogResolver(CatalogManager catalogManager,
			DependencyResourceResolver dependencyResourceResolver, Log log) {
		super(catalogManager);
		if (dependencyResourceResolver == null) {
			throw new IllegalArgumentException(
					"Dependency resource resolver must not be null.");
		}
		this.dependencyResourceResolver = dependencyResourceResolver;
		this.log = log != null ? log : NullLog.INSTANCE;
	}

	protected Log getLog() {
		return log;
	}

	@Override
	public String getResolvedEntity(String publicId, String systemId) {
		getLog().debug(
				MessageFormat.format("MavenCatalogResolver : Resolving publicId [{0}], systemId [{1}].", publicId, systemId));
		final String superResolvedEntity = super.getResolvedEntity(publicId, systemId);
		getLog().debug(
				MessageFormat.format("MavenCatalogResolver : Parent resolver has resolved publicId [{0}], systemId [{1}] to [{2}].", publicId, systemId, superResolvedEntity));
		if (superResolvedEntity != null) {
			systemId = superResolvedEntity;
		}

		if (systemId == null) {
			return null;
		}

		try {
			final URI uri = new URI(systemId);
			if (URI_SCHEME_MAVEN.equals(uri.getScheme())) {
				getLog().debug(
						MessageFormat.format("MavenCatalogResolver : Resolving systemId [{1}] as Maven dependency resource.", publicId, systemId));
				final String schemeSpecificPart = uri.getSchemeSpecificPart();
				try {
					final DependencyResource dependencyResource = DependencyResource.valueOf(schemeSpecificPart);
					try {
						final URL url = dependencyResourceResolver
								.resolveDependencyResource(dependencyResource);
						String resolved = url.toString();
						getLog().debug(
								MessageFormat.format(
										"MavenCatalogResolver : Resolved systemId [{1}] to [{2}].",
										publicId, systemId, resolved));
						return resolved;
					} catch (Exception ex) {
						getLog().error(
								MessageFormat
										.format("MavenCatalogResolver : Error resolving dependency resource [{0}].",
												dependencyResource));
					}
				} catch (IllegalArgumentException iaex) {
					getLog().error(
							MessageFormat
									.format("MavenCatalogResolver : Error parsing dependency descriptor [{0}].",
											schemeSpecificPart));

				}
				getLog().error(
						MessageFormat
								.format("MavenCatalogResolver : Failed to resolve systemId [{1}] as dependency resource. "
										+ "Returning parent resolver result [{2}].",
										publicId, systemId, superResolvedEntity));
				return superResolvedEntity;
			} else {
				getLog().debug(
						MessageFormat
								.format("MavenCatalogResolver : SystemId [{1}] is not a Maven dependency resource URI. "
										+ "Returning parent resolver result [{2}].",
										publicId, systemId, superResolvedEntity));
				return superResolvedEntity;
			}
		} catch (URISyntaxException urisex) {
			getLog().debug(
					MessageFormat
							.format("MavenCatalogResolver : Could not parse the systemId [{1}] as URI. "
									+ "Returning parent resolver result [{2}].",
									publicId, systemId, superResolvedEntity));
			return superResolvedEntity;
		}
	}

}
