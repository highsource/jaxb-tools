package org.jvnet.jaxb.xml.bind.model;

public interface MTypeInfoVisitor<T, C extends T, V> extends
		MClassTypeInfoVisitor<T, C, V> {

	public V visitList(MList<T, C> info);

	public V visitID(MID<T, C> info);

	public V visitIDREF(MIDREF<T, C> info);

	public V visitIDREFS(MIDREFS<T, C> info);

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo<T, C> info);

	public V visitEnumLeafInfo(MEnumLeafInfo<T, C> info);

	public V visitWildcardTypeInfo(MWildcardTypeInfo<T, C> info);

}
