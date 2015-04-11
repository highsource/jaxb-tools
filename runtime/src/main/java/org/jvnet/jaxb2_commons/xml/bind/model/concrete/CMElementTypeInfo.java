package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

public class CMElementTypeInfo<T, C extends T> implements
		MElementTypeInfo<T, C> {

	private final QName elementName;

	private final MTypeInfo<T, C> typeInfo;

	private final boolean nillable;

	private final String defaultValue;

	public CMElementTypeInfo(QName elementName, MTypeInfo<T, C> typeInfo,
			boolean nillable, String defaultValue) {
		Validate.notNull(elementName);
		Validate.notNull(typeInfo);
		this.elementName = elementName;
		this.typeInfo = typeInfo;
		this.nillable = nillable;
		this.defaultValue = defaultValue;
	}

	public QName getElementName() {
		return elementName;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

	public boolean isNillable() {
		return this.nillable;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String toString() {
		return "Element [" + getElementName() + ":" + getTypeInfo() + "]";
	}
}
