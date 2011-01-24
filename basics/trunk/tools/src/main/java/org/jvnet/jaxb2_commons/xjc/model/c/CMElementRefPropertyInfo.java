package org.jvnet.jaxb2_commons.xjc.model.c;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xjc.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;

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

	public MTypeInfo getTypeInfo() {
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

}
