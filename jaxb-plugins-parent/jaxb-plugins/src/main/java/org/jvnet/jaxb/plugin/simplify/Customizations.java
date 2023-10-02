package org.jvnet.jaxb.plugin.simplify;

import javax.xml.namespace.QName;

public class Customizations {

    public static String NAMESPACE_URI = "urn:jaxb.jvnet.org:plugin:simplify";

    public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI, "ignored");

    public static QName PROPERTY_ELEMENT_NAME = new QName(NAMESPACE_URI, "property");

    public static QName AS_ELEMENT_PROPERTY_ELEMENT_NAME = new QName(NAMESPACE_URI, "as-element-property");

    public static QName AS_REFERENCE_PROPERTY_ELEMENT_NAME = new QName(NAMESPACE_URI, "as-reference-property");

}
