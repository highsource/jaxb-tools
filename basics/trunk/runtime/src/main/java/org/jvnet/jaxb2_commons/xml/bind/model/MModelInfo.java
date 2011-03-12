package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MModelInfo<T, C> extends MOriginated<MModelInfoOrigin> {

	public Collection<MBuiltinLeafInfo<T, C>> getBuiltinLeafInfos();

	public MBuiltinLeafInfo<T, C> getBuiltinLeafInfo(QName name);

	public Collection<MClassInfo<T, C>> getClassInfos();

	public Collection<MEnumLeafInfo<T, C>> getEnumLeafInfos();

	public Collection<MTypeInfo<T, C>> getTypeInfos();

	public Collection<MElementInfo<T, C>> getElementInfos();

	public Map<QName, MElementInfo<T, C>> getElementInfosMap();

	public void addBuiltinLeafInfo(MBuiltinLeafInfo<T, C> builtinLeafInfo);

	public void addEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo);

	public void removeEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo);

	public void addClassInfo(MClassInfo<T, C> classInfo);

	public void removeClassInfo(MClassInfo<T, C> classInfo);

	public void addElementInfo(MElementInfo<T, C> elementInfo);

	public void removeElementInfo(MElementInfo<T, C> elementInfo);
}
