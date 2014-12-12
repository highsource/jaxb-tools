package org.jvnet.jaxb2.maven2.net;

import java.net.URI;
import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.logging.Log;

public abstract class AbstractSchemeAwareURILastModifiedResolver implements
		SchemeAwareURILastModifiedResolver {

	private final Log logger;
	private final String scheme;

	public AbstractSchemeAwareURILastModifiedResolver(String scheme, Log logger) {
		this.scheme = Validate.notNull(scheme);
		this.logger = Validate.notNull(logger);
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
		Validate.isTrue(scheme.equalsIgnoreCase(uri.getScheme()), MessageFormat
				.format("Invalid scheme [{0}] expected [{1}].",
						uri.getScheme(), scheme));
		return getLastModifiedForScheme(uri);
	}

	protected abstract Long getLastModifiedForScheme(URI uri);

}
