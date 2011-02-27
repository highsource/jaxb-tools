package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MModelInfo extends MOriginated<MModelInfoOrigin> {

	public Collection<MBuiltinLeafInfo> getBuiltinLeafInfos();

	public MBuiltinLeafInfo getBuiltinLeafInfo(QName name);

	public Collection<MClassInfo> getClassInfos();

	public Collection<MEnumLeafInfo> getEnumLeafInfos();

	public Collection<MTypeInfo> getTypeInfos();

	public Collection<MElementInfo> getElementInfos();

	public Map<QName, MElementInfo> getElementInfosMap();

	public void addBuiltinLeafInfo(MBuiltinLeafInfo builtinLeafInfo);

	public void addEnumLeafInfo(MEnumLeafInfo enumLeafInfo);

	public void removeEnumLeafInfo(MEnumLeafInfo enumLeafInfo);

	public void addClassInfo(MClassInfo classInfo);

	public void removeClassInfo(MClassInfo classInfo);

	public void addElementInfo(MElementInfo elementInfo);

	public void removeElementInfo(MElementInfo elementInfo);
}
