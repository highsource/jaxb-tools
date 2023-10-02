package org.jvnet.jaxb.plugin.customizations;

import javax.xml.namespace.QName;

/**
 * Customizations previous namespace handling
 *
 * @deprecated since 3.0, for removal
 */
@Deprecated
public class LegacyCustomizations {

	public static String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/customizations";

	public static String CUSTOMIZATIONS_LOCAL_PART = "customizations";

	public static QName CUSTOMIZATIONS_ELEMENT_NAME = new QName(NAMESPACE_URI, CUSTOMIZATIONS_LOCAL_PART);

}
