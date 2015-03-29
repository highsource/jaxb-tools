package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMElementPropertyInfo<T, C extends T> extends CMPropertyInfo<T, C> implements
		MElementPropertyInfo<T, C> {

	private final MTypeInfo<T, C> typeInfo;
	private final QName elementName;
	private final QName wrapperElementName;

	public CMElementPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			MTypeInfo<T, C> typeInfo, QName elementName,
			QName wrapperElementName) {
		super(origin, classInfo, privateName, collection);
		this.typeInfo = typeInfo;
		this.elementName = elementName;
		this.wrapperElementName = wrapperElementName;
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

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitElementPropertyInfo(this);
	}

}
