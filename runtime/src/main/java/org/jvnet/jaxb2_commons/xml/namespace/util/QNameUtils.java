package org.jvnet.jaxb2_commons.xml.namespace.util;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;

public class QNameUtils {

	private QNameUtils() {
	}

	public static String getKey(QName name) {
		Validate.notNull(name);
		final StringBuilder sb = new StringBuilder();
		final String namespaceURI = name.getNamespaceURI();
		if (!namespaceURI.equals(XMLConstants.NULL_NS_URI)) {
			sb.append("{").append(namespaceURI).append("}");
		}
		final String prefix = name.getPrefix();
		if (!XMLConstants.DEFAULT_NS_PREFIX.equals(prefix)) {
			sb.append(prefix).append(":");
		}
		final String localPart = name.getLocalPart();
		sb.append(localPart);
		return sb.toString();

	}
}
