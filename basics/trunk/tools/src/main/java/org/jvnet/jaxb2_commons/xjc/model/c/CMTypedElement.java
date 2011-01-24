package org.jvnet.jaxb2_commons.xjc.model.c;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypedElement;

public class CMTypedElement implements MTypedElement {

	private final QName elementName;

	private final MTypeInfo typeInfo;

	public CMTypedElement(QName elementName, MTypeInfo typeInfo) {
		Validate.notNull(elementName);
		Validate.notNull(typeInfo);
		this.elementName = elementName;
		this.typeInfo = typeInfo;
	}

	public QName getElementName() {
		return elementName;
	}

	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}
}
