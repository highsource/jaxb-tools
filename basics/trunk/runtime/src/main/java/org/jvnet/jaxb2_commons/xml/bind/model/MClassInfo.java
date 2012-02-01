package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MClassInfo<T, C> extends MPackagedTypeInfo<T, C>,
		MOriginated<MClassInfoOrigin> {

	public C getTargetClass();

	public MClassInfo<T, C> getBaseTypeInfo();

	public String getName();

	public String getLocalName();

	public List<MPropertyInfo<T, C>> getProperties();

	public QName getElementName();

	public MElementInfo<T, C> createElementInfo(MTypeInfo<T, C> scope,
			QName substitutionHead);

	public void addProperty(MPropertyInfo<T, C> propertyInfo);

	public void removeProperty(MPropertyInfo<T, C> propertyInfo);

}
