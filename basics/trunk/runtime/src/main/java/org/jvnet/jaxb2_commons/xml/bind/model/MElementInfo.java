package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

public interface MElementInfo extends MElementTypeInfo, MPackaged {

	@Override
	public QName getElementName();

	public MTypeInfo getScope();

	@Override
	public MTypeInfo getTypeInfo();

	public QName getSubstitutionHead();

}
