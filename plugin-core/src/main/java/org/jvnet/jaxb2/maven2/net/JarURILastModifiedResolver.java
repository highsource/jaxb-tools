package org.jvnet.jaxb2.maven2.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.logging.Log;

public class JarURILastModifiedResolver extends
		AbstractSchemeAwareURILastModifiedResolver {

	public static final String SCHEME = "jar";
	public static final String SEPARATOR = "!/";

	private URILastModifiedResolver parent;

	public JarURILastModifiedResolver(Log logger, URILastModifiedResolver parent) {
		super(SCHEME, logger);
		this.parent = Validate.notNull(parent);
	}

	private URILastModifiedResolver getParent() {
		return parent;
	}

	@Override
	protected Long getLastModifiedForScheme(URI uri) {
		try {
			final URI mainURI = getMainURI(uri);
			getLogger()
					.debug(MessageFormat
							.format("Retrieving the last modification timestamp of the URI [{0}] via the main URI [{1}].",
									uri, mainURI));
			return getParent().getLastModified(mainURI);
		} catch (Exception ex) {
			getLogger()
					.error(MessageFormat.format(
							"Could not retrieve the main URI from the Jar URI [{0}].",
							uri), ex);
			getLogger().warn(
					MessageFormat.format(
							"Last modification of the URI [{0}] is not known.",
							uri));
			return null;
		}
	}

	public URI getMainURI(URI uri) throws MalformedURLException,
			URISyntaxException {
		final String uriString = uri.toString();
		final URL url;
		if (uriString.indexOf(SEPARATOR) < 0) {
			url = new URI(uriString + SEPARATOR).toURL();
		} else {
			url = uri.toURL();
		}
		final String spec = url.getFile();
		final int separatorPosition = spec.indexOf(SEPARATOR);
		if (separatorPosition == -1) {
			throw new MalformedURLException(MessageFormat.format(
					"No [!/] found in url spec [{0}].", spec));
		}
		final String mainURIString = separatorPosition < 0 ? spec : spec
				.substring(0, separatorPosition);
		return new URI(mainURIString);
	}

}
