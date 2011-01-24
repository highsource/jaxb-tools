package org.jvnet.jaxb2_commons.xjc.model.c;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;

public class CMAttributePropertyInfo extends CMSingleTypePropertyInfo implements
		MAttributePropertyInfo {

	private final QName attributeName;

	public CMAttributePropertyInfo(String privateName, boolean collection,
			MTypeInfo typeInfo, QName attributeName) {
		super(privateName, collection, typeInfo);
		Validate.notNull(attributeName);
		this.attributeName = attributeName;
	}

	public QName getAttributeName() {
		return attributeName;
	}

}
