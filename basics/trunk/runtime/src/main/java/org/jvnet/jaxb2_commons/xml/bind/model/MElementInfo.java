package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MElementInfo extends MElementTypeInfo, MPackaged, MOriginated<MElementInfoOrigin> {

	@Override
	public QName getElementName();

	public MTypeInfo getScope();

	@Override
	public MTypeInfo getTypeInfo();

	public QName getSubstitutionHead();

}
