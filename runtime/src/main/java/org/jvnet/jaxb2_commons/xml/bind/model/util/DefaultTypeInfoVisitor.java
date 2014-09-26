package org.jvnet.jaxb2_commons.xml.bind.model.util;

import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MID;
import org.jvnet.jaxb2_commons.xml.bind.model.MIDREF;
import org.jvnet.jaxb2_commons.xml.bind.model.MIDREFS;
import org.jvnet.jaxb2_commons.xml.bind.model.MList;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MWildcardTypeInfo;

public class DefaultTypeInfoVisitor<T, C, V> implements
		MTypeInfoVisitor<T, C, V> {

	public V visitTypeInfo(MTypeInfo<T, C> typeInfo) {
		return null;
	}

	public V visitList(MList<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitID(MID<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitIDREF(MIDREF<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitIDREFS(MIDREFS<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitBuiltinLeafInfo(MBuiltinLeafInfo<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitEnumLeafInfo(MEnumLeafInfo<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitWildcardTypeInfo(MWildcardTypeInfo<T, C> info) {
		return visitTypeInfo(info);
	}

	public V visitClassInfo(MClassInfo<T, C> info) {
		return visitTypeInfo(info);
	}
	
	public V visitClassRef(MClassRef<T, C> info) {
		return visitTypeInfo(info);
	}
}
