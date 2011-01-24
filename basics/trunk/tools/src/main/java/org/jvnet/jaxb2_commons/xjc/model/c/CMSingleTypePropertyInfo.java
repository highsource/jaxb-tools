package org.jvnet.jaxb2_commons.xjc.model.c;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MSingleTypePropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;

public abstract class CMSingleTypePropertyInfo extends CMPropertyInfo implements
		MSingleTypePropertyInfo {

	private final MTypeInfo typeInfo;

	public CMSingleTypePropertyInfo(String privateName, boolean collection,
			MTypeInfo typeInfo) {
		super(privateName, collection);
		Validate.notNull(typeInfo);
		this.typeInfo = typeInfo;
	}

	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}

}
