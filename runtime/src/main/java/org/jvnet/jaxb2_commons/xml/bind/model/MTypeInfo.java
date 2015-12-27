package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MTypeInfo<T, C extends T> extends MCustomizable {

	public QName getTypeName();

	public boolean isSimpleType();

	public T getTargetType();

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor);
}
