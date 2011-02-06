package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

public class CMElementRefPropertyInfo extends CMPropertyInfo implements
		MElementRefPropertyInfo {

	private final MTypeInfo typeInfo;
	private final QName elementName;
	private final QName wrapperElementName;

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMElementRefPropertyInfo(String privateName, boolean collection,
			MTypeInfo typeInfo, QName elementName, QName wrapperElementName,
			boolean mixed, boolean domAllowed, boolean typedObjectAllowed) {
		super(privateName, collection);
		this.typeInfo = typeInfo;
		this.elementName = elementName;
		this.wrapperElementName = wrapperElementName;
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	@Override
	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}

	@Override
	public QName getElementName() {
		return elementName;
	}

	@Override
	public QName getWrapperElementName() {
		return wrapperElementName;
	}

	@Override
	public boolean isMixed() {
		return mixed;
	}

	@Override
	public boolean isDomAllowed() {
		return domAllowed;
	}

	@Override
	public boolean isTypedObjectAllowed() {
		return typedObjectAllowed;
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitElementRefPropertyInfo(this);
	}

}
