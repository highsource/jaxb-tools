package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MClassTypeInfoVisitor<T, C extends T, V> {

	public V visitClassInfo(MClassInfo<T, C> info);

	public V visitClassRef(MClassRef<T, C> info);

}
