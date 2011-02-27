package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MEnumLeafInfo extends MPackagedTypeInfo, MOriginated<MEnumLeafInfoOrigin> {

	public MTypeInfo getBaseTypeInfo();

	public List<MEnumConstantInfo> getConstants();
	
	public void addEnumConstantInfo(MEnumConstantInfo enumConstantInfo);

	public void removeEnumConstantInfo(MEnumConstantInfo enumConstantInfo);

	public QName getElementName();

	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead);

}
