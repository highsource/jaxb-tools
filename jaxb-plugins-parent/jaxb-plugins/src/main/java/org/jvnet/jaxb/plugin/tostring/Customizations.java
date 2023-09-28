package org.jvnet.jaxb.plugin.tostring;

import javax.xml.namespace.QName;

public class Customizations {

	public static String NAMESPACE_URI = "urn:jaxb.jvnet.org:plugin:toString";

	public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI, "ignored");

}
