package org.jvnet.jaxb.xml.bind.model.concrete;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.jvnet.jaxb.xml.bind.model.MElementTypeRef;
import org.jvnet.jaxb.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb.xml.bind.model.origin.MElementTypeRefOrigin;

public class CMElementTypeRef<T, C extends T> extends
		CMElementTypeInfo<T, C, MElementTypeRefOrigin> implements
		MElementTypeRef<T, C> {

	public CMElementTypeRef(MElementTypeRefOrigin origin, QName elementName,
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
