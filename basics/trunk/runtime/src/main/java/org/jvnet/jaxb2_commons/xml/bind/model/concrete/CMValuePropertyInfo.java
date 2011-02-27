package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MValuePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMValuePropertyInfo extends CMSingleTypePropertyInfo implements
		MValuePropertyInfo {

	public CMValuePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo classInfo, String privateName, MTypeInfo typeInfo) {
		super(origin, classInfo, privateName, false, typeInfo);
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitValuePropertyInfo(this);
	}

}
