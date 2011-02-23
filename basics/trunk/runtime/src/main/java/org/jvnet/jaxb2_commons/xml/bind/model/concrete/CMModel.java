package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

import com.sun.xml.bind.v2.model.core.ElementInfo;
import com.sun.xml.bind.v2.model.core.TypeInfoSet;

public class CMModel implements MModelInfo {

	private final TypeInfoSet<?, ?, ?, ?> typeInfoSet;

	private final Collection<MBuiltinLeafInfo> builtinLeafInfos = new ArrayList<MBuiltinLeafInfo>();
	private final Collection<MBuiltinLeafInfo> unmodifiableBuiltinLeafInfos = Collections
			.unmodifiableCollection(builtinLeafInfos);
	private final Map<QName, MBuiltinLeafInfo> builtinLeafInfosMap = new HashMap<QName, MBuiltinLeafInfo>();
	private final Map<QName, MBuiltinLeafInfo> unmodifiableBuiltinLeafInfosMap = Collections
			.unmodifiableMap(builtinLeafInfosMap);

	private final Collection<MClassInfo> classInfos = new ArrayList<MClassInfo>();
	private final Collection<MClassInfo> unmodifiableClassInfos = Collections
			.unmodifiableCollection(classInfos);

	private final Collection<MEnumLeafInfo> enumLeafInfos = new ArrayList<MEnumLeafInfo>();
	private final Collection<MEnumLeafInfo> unmodifiableEnumLeafInfos = Collections
			.unmodifiableCollection(enumLeafInfos);

	private final Collection<MTypeInfo> typeInfos = new ArrayList<MTypeInfo>();
	private final Collection<MTypeInfo> unmodifiableTypeInfos = Collections
			.unmodifiableCollection(typeInfos);

	private final Collection<MElementInfo> elementInfos = new ArrayList<MElementInfo>();
	private final Collection<MElementInfo> unmodifiableElementInfos = Collections
			.unmodifiableCollection(elementInfos);

	private final Map<QName, MElementInfo> elementInfosMap = new HashMap<QName, MElementInfo>();
	private final Map<QName, MElementInfo> unmodifiableElementInfosMap = Collections
			.unmodifiableMap(elementInfosMap);

	public CMModel(TypeInfoSet<?, ?, ?, ?> typeInfoSet) {
		Validate.notNull(typeInfoSet);
		this.typeInfoSet = typeInfoSet;
	}

	public TypeInfoSet<?, ?, ?, ?> getTypeInfoSet() {
		return typeInfoSet;
	}

	@Override
	public Collection<MBuiltinLeafInfo> getBuiltinLeafInfos() {
		return unmodifiableBuiltinLeafInfos;
	}

	@Override
	public Collection<MClassInfo> getClassInfos() {
		return unmodifiableClassInfos;
	}

	@Override
	public Collection<MEnumLeafInfo> getEnumLeafInfos() {
		return unmodifiableEnumLeafInfos;
	}

	@Override
	public Collection<MElementInfo> getElementInfos() {
		return unmodifiableElementInfos;
	}

	@Override
	public Map<QName, MElementInfo> getElementInfosMap() {
		return unmodifiableElementInfosMap;
	}

	@Override
	public Collection<MTypeInfo> getTypeInfos() {
		return unmodifiableTypeInfos;
	}

	@Override
	public MBuiltinLeafInfo getBuiltinLeafInfo(QName name) {
		Validate.notNull(name);
		return this.unmodifiableBuiltinLeafInfosMap.get(name);
	}

	@Override
	public MElementPropertyInfo createElementPropertyInfo(String privateName,
			boolean collection, MTypeInfo typeInfo, QName elementName,
			QName wrapperElementName) {
		return new CMElementPropertyInfo(privateName, collection, typeInfo,
				elementName, wrapperElementName);
	}

	@Override
	public void addBuiltinLeafInfo(MBuiltinLeafInfo builtinLeafInfo) {
		Validate.notNull(builtinLeafInfo);
		this.builtinLeafInfos.add(builtinLeafInfo);
		this.typeInfos.add(builtinLeafInfo);
		this.builtinLeafInfosMap.put(builtinLeafInfo.getTypeName(),
				builtinLeafInfo);
	}

	@Override
	public void addEnumLeafInfo(MEnumLeafInfo enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.add(enumLeafInfo);
		this.typeInfos.add(enumLeafInfo);
		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo elementInfo = enumLeafInfo.createElementInfo(
					null, null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
		}

	}

	@Override
	public void removeEnumLeafInfo(MEnumLeafInfo enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.remove(enumLeafInfo);
		this.typeInfos.remove(enumLeafInfo);
		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo elementInfo = this.elementInfosMap
					.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		// TODO Not very good
		if (enumLeafInfo instanceof CMEnumLeafInfo) {
			getTypeInfoSet().enums().remove(
					((CMEnumLeafInfo) enumLeafInfo).getEnumLeafInfo()
							.getClazz());
		}
	}

	@Override
	public void addClassInfo(MClassInfo classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.add(classInfo);
		this.typeInfos.add(classInfo);

		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			final MElementInfo elementInfo = classInfo.createElementInfo(null,
					null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
		}
	}

	@Override
	public void removeClassInfo(MClassInfo classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.add(classInfo);
		this.typeInfos.add(classInfo);
		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			final MElementInfo elementInfo = this.elementInfosMap
					.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		// TODO Not very good
		if (classInfo instanceof CMClassInfo) {
			getTypeInfoSet().beans().remove(
					((CMClassInfo) classInfo).getClassInfo().getClazz());
		}
	}

	@Override
	public void addElementInfo(MElementInfo elementInfo) {
		Validate.notNull(elementInfo);
		Validate.notNull(elementInfo.getElementName());
		this.elementInfos.add(elementInfo);
		this.elementInfosMap.put(elementInfo.getElementName(), elementInfo);

	}

	@Override
	public void removeElementInfo(MElementInfo elementInfo) {
		Validate.notNull(elementInfo);
		Validate.notNull(elementInfo.getElementName());
		this.elementInfos.remove(elementInfo);
		this.elementInfosMap.remove(elementInfo.getElementName());
		// TODO Not very good
		if (elementInfo instanceof CMElementInfo) {
			final List<ElementInfo<?, ?>> elementInfos = new ArrayList<ElementInfo<?, ?>>();
			for (ElementInfo<?, ?> ei : getTypeInfoSet().getAllElements()) {
				elementInfos.add(ei);
			}
			TypeInfoSet typeInfoSet2 = getTypeInfoSet();
			for (ElementInfo<?, ?> ei : elementInfos) {
				if (((CMElementInfo) elementInfo).getElementInfo() == ei) {
					typeInfoSet2.getElementMappings(ei.getScope().getClazz())
							.remove(ei.getElementName());
				}

			}
		}

	}
}
