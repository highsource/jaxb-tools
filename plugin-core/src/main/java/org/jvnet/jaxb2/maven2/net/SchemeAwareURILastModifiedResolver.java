package org.jvnet.jaxb2.maven2.net;

public interface SchemeAwareURILastModifiedResolver extends
		URILastModifiedResolver {

	public String getScheme();

}
