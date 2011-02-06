package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MValuePropertyInfo;

public class CMValuePropertyInfo extends CMSingleTypePropertyInfo implements
		MValuePropertyInfo {

	public CMValuePropertyInfo(String privateName, MTypeInfo typeInfo) {
		super(privateName, false, typeInfo);
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitValuePropertyInfo(this);
	}

}
