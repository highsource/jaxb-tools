package org.jvnet.jaxb.xml.bind.model;

public interface MTyped<T, C extends T> {

	public MTypeInfo<T, C> getTypeInfo();
}
