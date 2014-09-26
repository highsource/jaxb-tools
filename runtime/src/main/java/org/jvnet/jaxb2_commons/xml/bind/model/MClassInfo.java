package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MClassInfo<T, C> extends MClassTypeInfo<T, C>,
		MOriginated<MClassInfoOrigin> {

	public MClassTypeInfo<T, C> getBaseTypeInfo();

	public List<MPropertyInfo<T, C>> getProperties();

	public QName getElementName();

	public MElementInfo<T, C> createElementInfo(MTypeInfo<T, C> scope,
			QName substitutionHead);

	public void addProperty(MPropertyInfo<T, C> propertyInfo);

	public void removeProperty(MPropertyInfo<T, C> propertyInfo);

}
