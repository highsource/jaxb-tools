package org.jvnet.jaxb.plugin.mergeable;

import javax.xml.namespace.QName;

public class LegacyCustomizations {

	public static String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/mergeable";

	public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI,
			"ignored");

}
