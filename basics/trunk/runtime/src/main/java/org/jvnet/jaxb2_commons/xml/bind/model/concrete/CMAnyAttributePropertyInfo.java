package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMAnyAttributePropertyInfo extends CMPropertyInfo implements
		MAnyAttributePropertyInfo {

	public CMAnyAttributePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo classInfo, String privateName) {
		super(origin, classInfo, privateName, false);
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitAnyAttributePropertyInfo(this);
	}

}
