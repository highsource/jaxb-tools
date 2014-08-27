package org.jvnet.jaxb2_commons.xml.bind.model;


public interface MClassRef<T, C> extends MPackagedTypeInfo<T, C>, MContainer /*
																 * ,
																 * MOriginated<
																 * MClassInfoOrigin
																 * >, MContainer
																 */
{

	public C getTargetClass();
/*
	public MClassRef<T, C> getBaseTypeInfo();

	public String getName();

	public String getLocalName();

	public List<MPropertyInfo<T, C>> getProperties();

	public QName getElementName();

	public MElementInfo<T, C> createElementInfo(MTypeInfo<T, C> scope,
			QName substitutionHead);

	public void addProperty(MPropertyInfo<T, C> propertyInfo);

	public void removeProperty(MPropertyInfo<T, C> propertyInfo);*/

}
