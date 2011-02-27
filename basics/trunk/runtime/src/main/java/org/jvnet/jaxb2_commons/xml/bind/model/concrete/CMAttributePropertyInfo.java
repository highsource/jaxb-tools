package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMAttributePropertyInfo extends CMSingleTypePropertyInfo implements
		MAttributePropertyInfo {

	private final QName attributeName;

	public CMAttributePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo classInfo, String privateName, MTypeInfo typeInfo,
			QName attributeName) {
		super(origin, classInfo, privateName, false, typeInfo);
		Validate.notNull(attributeName);
		this.attributeName = attributeName;
	}

	@Override
	public QName getAttributeName() {
		return attributeName;
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitAttributePropertyInfo(this);
	}

}
