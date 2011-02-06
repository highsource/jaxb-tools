package org.jvnet.jaxb2_commons.xml.bind.model;

public abstract class MPackagedTypeInfoVisitor<V> implements
		MTypeInfoVisitor<V> {

	public abstract V visitPackagedTypeInfo(MPackagedTypeInfo info);

	@Override
	public V visitList(MList info) {
		return null;
	}

	@Override
	public V visitBuiltinLeafInfo(MBuiltinLeafInfo info) {
		return null;
	}

	@Override
	public V visitEnumLeafInfo(MEnumLeafInfo info) {
		return visitPackagedTypeInfo(info);
	}

	@Override
	public V visitWildcardTypeInfo(MWildcardTypeInfo info) {
		return null;
	}

	@Override
	public V visitClassInfo(MClassInfo info) {
		return visitPackagedTypeInfo(info);
	}

}
