package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

public interface MAttributePropertyInfo<T, C> extends
		MSingleTypePropertyInfo<T, C> {

	public QName getAttributeName();

}