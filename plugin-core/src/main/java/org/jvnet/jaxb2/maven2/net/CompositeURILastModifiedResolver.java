package org.jvnet.jaxb2.maven2.net;

import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb2.maven2.plugin.logging.NullLog;

public class CompositeURILastModifiedResolver implements
		URILastModifiedResolver {

	private final Map<String, URILastModifiedResolver> resolvers = new HashMap<String, URILastModifiedResolver>(
			10);

	private final Log logger;

	public CompositeURILastModifiedResolver(Log logger) {
		this.logger = Validate.notNull(logger);
		addResolvers(new FileURILastModifiedResolver(logger),
				new JarURILastModifiedResolver(logger, this),
				new HttpURILastModifiedResolver(logger),
				new HttpsURILastModifiedResolver(logger));
	}

	public CompositeURILastModifiedResolver() {
		this(NullLog.INSTANCE);
	}

	private Log getLogger() {
		return logger;
	}

	private void addResolvers(SchemeAwareURILastModifiedResolver... resolvers) {
		Validate.noNullElements(resolvers);
		for (final SchemeAwareURILastModifiedResolver resolver : resolvers) {
			this.resolvers.put(resolver.getScheme().toLowerCase(), resolver);
		}
	}

	private URILastModifiedResolver getResolver(String scheme) {
		return this.resolvers.get(scheme);
	}

	@Override
	public Long getLastModified(URI uri) {
		final String scheme = uri.getScheme();
		if (scheme == null) {
			getLogger()
					.error(MessageFormat.format(
							"URI [{0}] does not provide the scheme part.", uri));
		}
		final URILastModifiedResolver resolver = getResolver(scheme);
		if (resolver == null) {

			getLogger()
					.error(MessageFormat
							.format("Could not resolve the last modification of the URI [{0}] with the scheme [{1}].",
									uri, scheme));
		} else {
			return resolver.getLastModified(uri);
		}
		getLogger()
				.warn(MessageFormat
						.format("Last modification of the URI [{0}] is not known.",
								uri));
		return null;
	}
}
