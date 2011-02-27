package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MClassInfo extends MPackagedTypeInfo,
		MOriginated<MClassInfoOrigin> {

	public MClassInfo getBaseTypeInfo();

	public String getName();

	public String getLocalName();

	public List<MPropertyInfo> getProperties();

	public QName getElementName();

	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead);

	public void addProperty(MPropertyInfo propertyInfo);

	public void removeProperty(MPropertyInfo propertyInfo);

}
