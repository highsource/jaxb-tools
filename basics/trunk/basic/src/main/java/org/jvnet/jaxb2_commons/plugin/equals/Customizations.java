package org.jvnet.jaxb2_commons.plugin.equals;

import javax.xml.namespace.QName;

public class Customizations {

	public static String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/equals";

	public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI,
			"ignored");

}
