package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MValuePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMValuePropertyInfo<T, C> extends CMSingleTypePropertyInfo<T, C>
		implements MValuePropertyInfo<T, C> {

	public CMValuePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName,
			MTypeInfo<T, C> typeInfo) {
		super(origin, classInfo, privateName, false, typeInfo);
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitValuePropertyInfo(this);
	}

}
