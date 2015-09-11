package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMElementPropertyInfo<T, C extends T> extends CMPropertyInfo<T, C>
		implements MElementPropertyInfo<T, C> {

	private final MTypeInfo<T, C> typeInfo;
	private final QName elementName;
	private final QName wrapperElementName;
	private final boolean nillable;
	private final String defaultValue;

	public CMElementPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			boolean required, MTypeInfo<T, C> typeInfo, QName elementName,
			QName wrapperElementName, boolean nillable, String defaultValue) {
		super(origin, classInfo, privateName, collection, required);
		this.typeInfo = typeInfo;
		this.elementName = elementName;
		this.wrapperElementName = wrapperElementName;
		this.nillable = nillable;
		this.defaultValue = defaultValue;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

	public QName getElementName() {
		return elementName;
	}

	public QName getWrapperElementName() {
		return wrapperElementName;
	}

	@Override
	public boolean isNillable() {
		return nillable;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitElementPropertyInfo(this);
	}

}
