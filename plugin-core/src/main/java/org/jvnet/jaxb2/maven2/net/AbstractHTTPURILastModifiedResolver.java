package org.jvnet.jaxb2.maven2.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

import org.apache.maven.plugin.logging.Log;

public abstract class AbstractHTTPURILastModifiedResolver extends
		AbstractSchemeAwareURILastModifiedResolver {

	public AbstractHTTPURILastModifiedResolver(String scheme, Log logger) {
		super(scheme, logger);
	}

	@Override
	protected Long getLastModifiedForScheme(URI uri) {
		getLogger()
				.warn(MessageFormat
						.format("The URI [{0}] seems to represent an absolute HTTP or HTTPS URL. "
								+ "Getting the last modification timestamp is only possible "
								+ "if the URL is accessible "
								+ "and if the server returns the [Last-Modified] header correctly. "
								+ "This method is not reliable and is likely to fail. "
								+ "In this case the last modification timestamp will be assumed to be unknown.",
								uri));
		try {
			final URL url = uri.toURL();
			try {
				final URLConnection urlConnection = url.openConnection();
				if (urlConnection instanceof HttpURLConnection) {
					final HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
					httpURLConnection.setInstanceFollowRedirects(true);
					final long lastModified = httpURLConnection
							.getLastModified();
					if (lastModified == 0) {
						getLogger()
								.error(MessageFormat
										.format("Could not retrieve the last modification timestamp for the URI [{0}] from the HTTP URL connection. "
												+ "The [Last-Modified] header was probably not set correctly.",
												uri));

					} else {
						getLogger()
								.debug(MessageFormat
										.format("HTTP connection to the URI [{0}] returned the last modification timestamp [{1,date,yyyy-MM-dd HH:mm:ss.SSS}].",
												uri, lastModified));
						return lastModified;
					}
				} else {
					getLogger()
							.error(MessageFormat
									.format("URL connection for the URI [{0}] is not a HTTP or HTTPS connection, can't read the [Last-Modified] header.",
											uri));
				}
			} catch (IOException ioex) {
				getLogger()
						.error(MessageFormat.format(
								"Error opening the URL connection for the URI [{0}].",
								uri), ioex);
			}
		} catch (MalformedURLException murlex) {
			getLogger().error(MessageFormat.format("URI [{0}].", uri), murlex);
		}
		getLogger()
				.warn(MessageFormat
						.format("Last modification of the URI [{0}] is not known.",
								uri));
		return null;
	}
}
