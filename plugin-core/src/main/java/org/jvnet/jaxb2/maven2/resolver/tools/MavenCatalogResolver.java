package org.jvnet.jaxb2.maven2.resolver.tools;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb2.maven2.DependencyResource;
import org.jvnet.jaxb2.maven2.DependencyResourceResolver;
import org.jvnet.jaxb2.maven2.plugin.logging.NullLog;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;

public class MavenCatalogResolver extends
		com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver {

	public static final String URI_SCHEME_MAVEN = "maven";
	private final DependencyResourceResolver dependencyResourceResolver;
	private final CatalogManager catalogManager;
	private final Log log;

	public MavenCatalogResolver(CatalogManager catalogManager,
			DependencyResourceResolver dependencyResourceResolver) {
		this(catalogManager, dependencyResourceResolver, NullLog.INSTANCE);
	}

	public MavenCatalogResolver(CatalogManager catalogManager,
			DependencyResourceResolver dependencyResourceResolver, Log log) {
		super(catalogManager);
		this.catalogManager = catalogManager;
		if (dependencyResourceResolver == null) {
			throw new IllegalArgumentException(
					"Dependency resource resolver must not be null.");
		}
		this.dependencyResourceResolver = dependencyResourceResolver;
		this.log = log != null ? log : NullLog.INSTANCE;
	}

	protected CatalogManager getCatalogManager() {
		return catalogManager;
	}

	protected Log getLog() {
		return log;
	}

	@Override
	public String getResolvedEntity(String publicId, String systemId) {
		getLog().debug(
				MessageFormat.format(
						"Resolving publicId [{0}], systemId [{1}].", publicId,
						systemId));
		final String superResolvedEntity = super.getResolvedEntity(publicId,
				systemId);
		getLog().debug(
				MessageFormat
						.format("Parent resolver has resolved publicId [{0}], systemId [{1}] to [{2}].",
								publicId, systemId, superResolvedEntity));
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
						MessageFormat
								.format("Resolving systemId [{1}] as Maven dependency resource.",
										publicId, systemId));
				final String schemeSpecificPart = uri.getSchemeSpecificPart();
				try {
					final DependencyResource dependencyResource = DependencyResource
							.valueOf(schemeSpecificPart);
					try {
						final URL url = dependencyResourceResolver
								.resolveDependencyResource(dependencyResource);
						String resolved = url.toString();
						getLog().debug(
								MessageFormat.format(
										"Resolved systemId [{1}] to [{2}].",
										publicId, systemId, resolved));
						return resolved;
					} catch (Exception ex) {
						getLog().error(
								MessageFormat
										.format("Error resolving dependency resource [{0}].",
												dependencyResource));
					}
				} catch (IllegalArgumentException iaex) {
					getLog().error(
							MessageFormat
									.format("Error parsing dependency descriptor [{0}].",
											schemeSpecificPart));

				}
				getLog().error(
						MessageFormat
								.format("Failed to resolve systemId [{1}] as dependency resource. "
										+ "Returning parent resolver result [{2}].",
										publicId, systemId, superResolvedEntity));
				return superResolvedEntity;
			} else {
				getLog().debug(
						MessageFormat
								.format("SystemId [{1}] is not a Maven dependency resource URI. "
										+ "Returning parent resolver result [{2}].",
										publicId, systemId, superResolvedEntity));
				return superResolvedEntity;
			}
		} catch (URISyntaxException urisex) {
			getLog().debug(
					MessageFormat
							.format("Coul not parse the systemId [{1}] as URI. "
									+ "Returning parent resolver result [{2}].",
									publicId, systemId, superResolvedEntity));
			return superResolvedEntity;
		}
	}

}
