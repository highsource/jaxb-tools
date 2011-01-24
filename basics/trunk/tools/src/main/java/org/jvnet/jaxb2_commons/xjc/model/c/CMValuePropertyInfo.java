package org.jvnet.jaxb2_commons.xjc.model.c;

import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xjc.model.MValuePropertyInfo;

public class CMValuePropertyInfo extends CMSingleTypePropertyInfo implements
		MValuePropertyInfo {

	public CMValuePropertyInfo(String privateName, boolean collection,
			MTypeInfo typeInfo) {
		super(privateName, collection, typeInfo);
	}

}
