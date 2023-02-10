package org.jvnet.jaxb.net;

import org.apache.maven.plugin.logging.Log;

public class HttpURILastModifiedResolver extends AbstractHTTPURILastModifiedResolver{

	public static final String SCHEME = "http";
	
	public HttpURILastModifiedResolver(Log logger) {
		super(SCHEME, logger);
	}

}
