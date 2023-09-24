package org.jvnet.jaxb.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.xml.bind.model.origin.MClassInfoOrigin;

public interface MClassInfo<T, C extends T> extends
		MClassTypeInfo<T, C, MClassInfoOrigin> {

	public MClassTypeInfo<T, C, ?> getBaseTypeInfo();

	public List<MPropertyInfo<T, C>> getProperties();

	public MPropertyInfo<T, C> getProperty(String publicName);

	public QName getElementName();

	public MElementInfo<T, C> createElementInfo(MClassInfo<T, C> scope,
			QName substitutionHead);

	public void addProperty(MPropertyInfo<T, C> propertyInfo);

	public void removeProperty(MPropertyInfo<T, C> propertyInfo);

}
