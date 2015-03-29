package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

public interface MTypeInfo<T, C extends T> extends MCustomizable {

	public QName getTypeName();

	public boolean isSimpleType();

	public T getTargetType();

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor);
}
