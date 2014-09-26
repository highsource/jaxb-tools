package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

public class CMElementTypeInfo<T, C> implements MElementTypeInfo<T, C> {

	private final QName elementName;

	private final MTypeInfo<T, C> typeInfo;

	public CMElementTypeInfo(QName elementName, MTypeInfo<T, C> typeInfo) {
		Validate.notNull(elementName);
		Validate.notNull(typeInfo);
		this.elementName = elementName;
		this.typeInfo = typeInfo;
	}

	public QName getElementName() {
		return elementName;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

	@Override
	public String toString() {
		return "Element [" + getElementName() + ":" + getTypeInfo() + "]";
	}
}
