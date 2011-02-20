package org.jvnet.jaxb2_commons.xml.bind.model.util;

import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MList;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MWildcardTypeInfo;

public class DefaultTypeInfoVisitor<V> implements MTypeInfoVisitor<V> {

	public V visitTypeInfo(MTypeInfo typeInfo) {
		return null;
	}

	@Override
	public V visitList(MList info) {
		return visitTypeInfo(info);
	}

	@Override
	public V visitBuiltinLeafInfo(MBuiltinLeafInfo info) {
		return visitTypeInfo(info);
	}

	@Override
	public V visitEnumLeafInfo(MEnumLeafInfo info) {
		return visitTypeInfo(info);
	}

	@Override
	public V visitWildcardTypeInfo(MWildcardTypeInfo info) {
		return visitTypeInfo(info);
	}

	@Override
	public V visitClassInfo(MClassInfo info) {
		return visitTypeInfo(info);
	}

}
