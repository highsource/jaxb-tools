package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

public interface MElementTypeInfo<T, C extends T> extends MTyped<T, C>,
		MNillable {

	public QName getElementName();

}
