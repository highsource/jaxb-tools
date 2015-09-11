package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMAnyAttributePropertyInfo<T, C extends T> extends
		CMPropertyInfo<T, C> implements MAnyAttributePropertyInfo<T, C> {

	public CMAnyAttributePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName) {
		super(origin, classInfo, privateName, false, false);
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitAnyAttributePropertyInfo(this);
	}

}
