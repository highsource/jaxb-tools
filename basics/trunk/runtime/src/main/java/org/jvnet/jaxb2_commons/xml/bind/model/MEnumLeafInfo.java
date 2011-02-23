package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

import javax.xml.namespace.QName;

public interface MEnumLeafInfo extends MPackagedTypeInfo {

	public MTypeInfo getBaseTypeInfo();

	public List<MEnumConstantInfo> getConstants();

	public QName getElementName();

	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead);

}
