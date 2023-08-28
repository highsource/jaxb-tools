package org.jvnet.jaxb.maven.net;

import java.net.URI;

public interface URILastModifiedResolver {

	/**
	 * Finds out the last modification date for an URI.
	 *
	 * @param uri
	 *            URI to find out the last modification date for.
	 * @return Last modification date or <code>null</code> if unknown.
	 */
	public Long getLastModified(URI uri);
}
