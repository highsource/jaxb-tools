package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MTypeInfoVisitor<T, C, V> {

	public V visitList(MList<T, C> info);

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo<T, C> info);

	public V visitEnumLeafInfo(MEnumLeafInfo<T, C> info);

	public V visitWildcardTypeInfo(MWildcardTypeInfo<T, C> info);

	public V visitClassInfo(MClassInfo<T, C> info);

}
