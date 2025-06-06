package org.jvnet.jaxb.maven.net;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Objects;

import org.apache.maven.plugin.logging.Log;

public abstract class AbstractSchemeAwareURILastModifiedResolver implements
		SchemeAwareURILastModifiedResolver {

	private final Log logger;
	private final String scheme;

	public AbstractSchemeAwareURILastModifiedResolver(String scheme, Log logger) {
		this.scheme = Objects.requireNonNull(scheme, "scheme must not be null.");
		this.logger = Objects.requireNonNull(logger, "logger must not be null.");
	}

	@Override
	public String getScheme() {
		return scheme;
	}

	protected Log getLogger() {
		return logger;
	}

	@Override
	public Long getLastModified(URI uri) {
		final String scheme = getScheme();
		if (!scheme.equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException(MessageFormat
                .format("Invalid scheme [{0}] expected [{1}].",
                    uri.getScheme(), scheme));
        }
		return getLastModifiedForScheme(uri);
	}

	protected abstract Long getLastModifiedForScheme(URI uri);

}
