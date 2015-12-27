package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MElement;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementOrigin;

public class CMElement<T, C extends T> extends
		CMElementTypeInfo<T, C, MElementOrigin> implements MElement<T, C> {

	public CMElement(MElementOrigin origin, QName elementName,
			MTypeInfo<T, C> typeInfo, boolean nillable, String defaultValue,
			NamespaceContext defaultValueNamespaceContext) {
		super(origin, elementName, typeInfo, nillable, defaultValue,
				defaultValueNamespaceContext);
	}

	@Override
	public String toString() {
		return "Element [" + getElementName() + ":" + getTypeInfo() + "]";
	}
}
