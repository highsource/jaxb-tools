package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMElementRefPropertyInfo<T, C extends T> extends CMPropertyInfo<T, C>
		implements MElementRefPropertyInfo<T, C> {

	private final MTypeInfo<T, C> typeInfo;
	private final QName elementName;
	private final QName wrapperElementName;

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;
	private final String defaultValue;

	public CMElementRefPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			MTypeInfo<T, C> typeInfo, QName elementName,
			QName wrapperElementName, boolean mixed, boolean domAllowed,
			boolean typedObjectAllowed, String defaultValue) {
		super(origin, classInfo, privateName, collection);
		this.typeInfo = typeInfo;
		this.elementName = elementName;
		this.wrapperElementName = wrapperElementName;
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
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

	public boolean isMixed() {
		return mixed;
	}

	public boolean isDomAllowed() {
		return domAllowed;
	}

	public boolean isTypedObjectAllowed() {
		return typedObjectAllowed;
	}
	
	@Override
	public boolean isNillable() {
		return true;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitElementRefPropertyInfo(this);
	}

}
