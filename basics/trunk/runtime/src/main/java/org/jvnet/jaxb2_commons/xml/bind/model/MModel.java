package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

public interface MModel {

	public Collection<MBuiltinLeafInfo> getBuiltinLeafInfos();

	public MBuiltinLeafInfo getBuiltinLeafInfo(QName name);

	public Collection<MClassInfo> getClassInfos();

	public Collection<MEnumLeafInfo> getEnumLeafInfos();

	public Collection<MTypeInfo> getTypeInfos();

	public Collection<MElementInfo> getElementInfos();

	public Map<QName, MElementInfo> getElementInfosMap();

}
