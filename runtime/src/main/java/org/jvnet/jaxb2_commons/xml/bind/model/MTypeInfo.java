package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MTypeInfo<T, C> extends MCustomizable {

	public T getTargetType();

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor);
}
