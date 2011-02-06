package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;

public class CMAnyAttributePropertyInfo extends CMPropertyInfo implements
		MAnyAttributePropertyInfo {

	public CMAnyAttributePropertyInfo(String privateName) {
		super(privateName, false);
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitAnyAttributePropertyInfo(this);
	}

}
