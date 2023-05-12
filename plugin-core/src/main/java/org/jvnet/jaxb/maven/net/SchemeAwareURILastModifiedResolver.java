package org.jvnet.jaxb.maven.net;

public interface SchemeAwareURILastModifiedResolver extends
		URILastModifiedResolver {

	public String getScheme();

}
