package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

public interface MElementTypeInfo extends MTyped {

	public QName getElementName();

	@Override
	public MTypeInfo getTypeInfo();

}
