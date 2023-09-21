package org.jvnet.jaxb.plugin.wildcard;

import javax.xml.namespace.QName;

public class Customizations {

	public static String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/wildcard";

	public static QName LAX_ELEMENT_NAME = new QName(NAMESPACE_URI, "lax");
	public static QName STRICT_ELEMENT_NAME = new QName(NAMESPACE_URI, "strict");
	public static QName SKIP_ELEMENT_NAME = new QName(NAMESPACE_URI, "skip");

}
