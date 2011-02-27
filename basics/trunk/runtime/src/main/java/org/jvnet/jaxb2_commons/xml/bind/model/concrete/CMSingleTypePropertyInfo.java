package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MSingleTypePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMSingleTypePropertyInfo extends CMPropertyInfo implements
		MSingleTypePropertyInfo {

	private final MTypeInfo typeInfo;

	public CMSingleTypePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo classInfo, String privateName, boolean collection,
			MTypeInfo typeInfo) {
		super(origin, classInfo, privateName, collection);
		Validate.notNull(typeInfo);
		this.typeInfo = typeInfo;
	}

	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}

}
