package org.jvnet.jaxb.xml.bind.model;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb.xml.bind.model.origin.MOriginated;

public interface MModelInfo<T, C extends T> extends MCustomizable,
		MOriginated<MModelInfoOrigin> {

	public Collection<MBuiltinLeafInfo<T, C>> getBuiltinLeafInfos();

	public Collection<MClassInfo<T, C>> getClassInfos();

	public MClassInfo<T, C> getClassInfo(String name);

	public Collection<MEnumLeafInfo<T, C>> getEnumLeafInfos();

	public Collection<MTypeInfo<T, C>> getTypeInfos();

	public MTypeInfo<T, C> getTypeInfo(QName typeNam);

	public Collection<MElementInfo<T, C>> getElementInfos();

	public MElementInfo<T, C> getGlobalElementInfo(QName elementName);

	public void addBuiltinLeafInfo(MBuiltinLeafInfo<T, C> builtinLeafInfo);

	public void addEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo);

	public void removeEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo);

	public void addClassInfo(MClassInfo<T, C> classInfo);

	public void removeClassInfo(MClassInfo<T, C> classInfo);

	public void addElementInfo(MElementInfo<T, C> elementInfo);

	public void removeElementInfo(MElementInfo<T, C> elementInfo);
}
