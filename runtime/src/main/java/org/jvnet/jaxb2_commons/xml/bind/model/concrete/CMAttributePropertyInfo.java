package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMAttributePropertyInfo<T, C extends T> extends
		CMSingleTypePropertyInfo<T, C> implements MAttributePropertyInfo<T, C> {

	private final QName attributeName;

	public CMAttributePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName,
			MTypeInfo<T, C> typeInfo, QName attributeName) {
		super(origin, classInfo, privateName, false, typeInfo);
		Validate.notNull(attributeName);
		this.attributeName = attributeName;
	}

	public QName getAttributeName() {
		return attributeName;
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitAttributePropertyInfo(this);
	}

}
