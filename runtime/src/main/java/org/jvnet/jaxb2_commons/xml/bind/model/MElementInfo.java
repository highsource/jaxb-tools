package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

public interface MElementInfo<T, C extends T> extends
		MElementTypeInfo<T, C, MElementInfoOrigin>, MPackaged, MContained,
		MContainer {

	public QName getElementName();

	public MClassInfo<T, C> getScope();

	public QName getSubstitutionHead();

}
