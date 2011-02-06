package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MTypeInfoVisitor<V> {

	public V visitList(MList info);

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo info);

	public V visitEnumLeafInfo(MEnumLeafInfo info);

	public V visitWildcardTypeInfo(MWildcardTypeInfo info);

	public V visitClassInfo(MClassInfo info);

}
