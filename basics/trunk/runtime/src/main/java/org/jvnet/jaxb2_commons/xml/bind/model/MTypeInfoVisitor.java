package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MTypeInfoVisitor<T, C, V> {

	public V visitList(MList<T, C> info);

	public V visitID(MID<T, C> info);

	public V visitIDREF(MIDREF<T, C> info);

	public V visitIDREFS(MIDREFS<T, C> info);

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo<T, C> info);

	public V visitEnumLeafInfo(MEnumLeafInfo<T, C> info);

	public V visitWildcardTypeInfo(MWildcardTypeInfo<T, C> info);

	public V visitClassInfo(MClassInfo<T, C> info);

	public V visitClassRef(MClassRef<T, C> info);
}
