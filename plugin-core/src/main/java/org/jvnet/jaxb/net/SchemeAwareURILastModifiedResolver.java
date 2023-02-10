package org.jvnet.jaxb.net;

public interface SchemeAwareURILastModifiedResolver extends
		URILastModifiedResolver {

	public String getScheme();

}
