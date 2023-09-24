package org.jvnet.jaxb.plugin;

import javax.xml.namespace.QName;

public class Customizations {

	public static String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic";

	public static QName GENERATED_ELEMENT_NAME = new QName(NAMESPACE_URI,
			"generated");

	public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI,
			"ignored");

	public static QName PROPERTY_ELEMENT_NAME = new QName(NAMESPACE_URI,
			"property");

}
