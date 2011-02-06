package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MSingleTypePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

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
