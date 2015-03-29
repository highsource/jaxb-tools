package org.jvnet.jaxb2_commons.xml.bind.model;

public abstract class MPackagedTypeInfoVisitor<T, C extends T, V> implements
		MTypeInfoVisitor<T, C, V> {

	public abstract V visitPackagedTypeInfo(MPackagedTypeInfo<T, C> info);

	public V visitList(MList<T, C> info) {
		return null;
	}

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo<T, C> info) {
		return null;
	}

	public V visitID(MID<T, C> info) {
		return null;
	}

	public V visitIDREF(MIDREF<T, C> info) {
		return null;
	}

	public V visitIDREFS(MIDREFS<T, C> info) {
		return null;
	}

	public V visitEnumLeafInfo(MEnumLeafInfo<T, C> info) {
		return visitPackagedTypeInfo(info);
	}

	public V visitWildcardTypeInfo(MWildcardTypeInfo<T, C> info) {
		return null;
	}

	public V visitClassInfo(MClassInfo<T, C> info) {
		return visitPackagedTypeInfo(info);
	}

}
