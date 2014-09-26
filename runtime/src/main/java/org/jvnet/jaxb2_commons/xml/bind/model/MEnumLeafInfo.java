package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MEnumLeafInfo<T, C> extends MPackagedTypeInfo<T, C>,
		MOriginated<MEnumLeafInfoOrigin> {

	public C getTargetClass();

	public MTypeInfo<T, C> getBaseTypeInfo();

	public List<MEnumConstantInfo<T, C>> getConstants();

	public void addEnumConstantInfo(MEnumConstantInfo<T, C> enumConstantInfo);

	public void removeEnumConstantInfo(MEnumConstantInfo<T, C> enumConstantInfo);

	public QName getElementName();

	public MElementInfo<T, C> createElementInfo(MTypeInfo<T, C> scope,
			QName substitutionHead);

}
