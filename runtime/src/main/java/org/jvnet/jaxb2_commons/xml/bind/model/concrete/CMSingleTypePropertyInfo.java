package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MSingleTypePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMSingleTypePropertyInfo<T, C extends T> extends
		CMPropertyInfo<T, C> implements MSingleTypePropertyInfo<T, C> {

	private final MTypeInfo<T, C> typeInfo;

	public CMSingleTypePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			MTypeInfo<T, C> typeInfo, boolean required) {
		super(origin, classInfo, privateName, collection, required);
		Validate.notNull(typeInfo);
		this.typeInfo = typeInfo;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

}
