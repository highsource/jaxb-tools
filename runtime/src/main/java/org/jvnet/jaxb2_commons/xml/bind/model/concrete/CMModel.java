package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.ClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.ElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.EnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.TypeInfoSetOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;

import com.sun.xml.bind.v2.model.core.ClassInfo;
import com.sun.xml.bind.v2.model.core.ElementInfo;
import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
import com.sun.xml.bind.v2.model.core.TypeInfoSet;

public class CMModel<T, C> implements MModelInfo<T, C> {

	private final MModelInfoOrigin origin;

	private final CMCustomizations customizations = new CMCustomizations();

	private final Collection<MBuiltinLeafInfo<T, C>> builtinLeafInfos = new ArrayList<MBuiltinLeafInfo<T, C>>();
	private final Collection<MBuiltinLeafInfo<T, C>> unmodifiableBuiltinLeafInfos = Collections
			.unmodifiableCollection(builtinLeafInfos);
	private final Map<QName, MBuiltinLeafInfo<T, C>> builtinLeafInfosMap = new HashMap<QName, MBuiltinLeafInfo<T, C>>();
	private final Map<QName, MBuiltinLeafInfo<T, C>> unmodifiableBuiltinLeafInfosMap = Collections
			.unmodifiableMap(builtinLeafInfosMap);

	private final Collection<MClassInfo<T, C>> classInfos = new ArrayList<MClassInfo<T, C>>();
	private final Collection<MClassInfo<T, C>> unmodifiableClassInfos = Collections
			.unmodifiableCollection(classInfos);

	private final Collection<MEnumLeafInfo<T, C>> enumLeafInfos = new ArrayList<MEnumLeafInfo<T, C>>();
	private final Collection<MEnumLeafInfo<T, C>> unmodifiableEnumLeafInfos = Collections
			.unmodifiableCollection(enumLeafInfos);

	private final Collection<MTypeInfo<T, C>> typeInfos = new ArrayList<MTypeInfo<T, C>>();
	private final Collection<MTypeInfo<T, C>> unmodifiableTypeInfos = Collections
			.unmodifiableCollection(typeInfos);

	private final Collection<MElementInfo<T, C>> elementInfos = new ArrayList<MElementInfo<T, C>>();
	private final Collection<MElementInfo<T, C>> unmodifiableElementInfos = Collections
			.unmodifiableCollection(elementInfos);

	private final Map<QName, MElementInfo<T, C>> elementInfosMap = new HashMap<QName, MElementInfo<T, C>>();
	private final Map<QName, MElementInfo<T, C>> unmodifiableElementInfosMap = Collections
			.unmodifiableMap(elementInfosMap);

	public CMModel(MModelInfoOrigin origin) {
		Validate.notNull(origin);
		this.origin = origin;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public MModelInfoOrigin getOrigin() {
		return origin;
	}

	public Collection<MBuiltinLeafInfo<T, C>> getBuiltinLeafInfos() {
		return unmodifiableBuiltinLeafInfos;
	}

	public Collection<MClassInfo<T, C>> getClassInfos() {
		return unmodifiableClassInfos;
	}

	public Collection<MEnumLeafInfo<T, C>> getEnumLeafInfos() {
		return unmodifiableEnumLeafInfos;
	}

	public Collection<MElementInfo<T, C>> getElementInfos() {
		return unmodifiableElementInfos;
	}

	public Map<QName, MElementInfo<T, C>> getElementInfosMap() {
		return unmodifiableElementInfosMap;
	}

	public Collection<MTypeInfo<T, C>> getTypeInfos() {
		return unmodifiableTypeInfos;
	}

	public MBuiltinLeafInfo<T, C> getBuiltinLeafInfo(QName name) {
		Validate.notNull(name);
		return this.unmodifiableBuiltinLeafInfosMap.get(name);
	}

	public void addBuiltinLeafInfo(MBuiltinLeafInfo<T, C> builtinLeafInfo) {
		Validate.notNull(builtinLeafInfo);
		this.builtinLeafInfos.add(builtinLeafInfo);
		this.typeInfos.add(builtinLeafInfo);
		this.builtinLeafInfosMap.put(builtinLeafInfo.getTypeName(),
				builtinLeafInfo);
	}

	public void addEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.add(enumLeafInfo);
		this.typeInfos.add(enumLeafInfo);
		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = enumLeafInfo
					.createElementInfo(null, null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
		}

	}

	public void removeEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.remove(enumLeafInfo);
		this.typeInfos.remove(enumLeafInfo);
		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = this.elementInfosMap
					.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		// TODO Not very good
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& enumLeafInfo.getOrigin() instanceof EnumLeafInfoOrigin) {

			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();

			final EnumLeafInfo<T, C> eli = ((EnumLeafInfoOrigin<T, C, EnumLeafInfo<T, C>>) enumLeafInfo
					.getOrigin()).getSource();
			tis.enums().remove(eli.getClazz());
		}
	}

	public void addClassInfo(MClassInfo<T, C> classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.add(classInfo);
		this.typeInfos.add(classInfo);

		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = classInfo.createElementInfo(
					null, null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
		}
	}

	public void removeClassInfo(MClassInfo<T, C> classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.remove(classInfo);
		this.typeInfos.remove(classInfo);
		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = this.elementInfosMap
					.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& classInfo.getOrigin() instanceof ClassInfoOrigin) {
			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();
			final ClassInfo<T, C> ci = ((ClassInfoOrigin<T, C, ClassInfo<T, C>>) classInfo
					.getOrigin()).getSource();
			tis.beans().remove(ci);

		}
	}

	public void addElementInfo(MElementInfo<T, C> elementInfo) {
		Validate.notNull(elementInfo);
		Validate.notNull(elementInfo.getElementName());
		this.elementInfos.add(elementInfo);
		this.elementInfosMap.put(elementInfo.getElementName(), elementInfo);

	}

	public void removeElementInfo(MElementInfo<T, C> elementInfo) {
		Validate.notNull(elementInfo);
		Validate.notNull(elementInfo.getElementName());
		this.elementInfos.remove(elementInfo);
		this.elementInfosMap.remove(elementInfo.getElementName());
		// TODO Not very good
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& elementInfo.getOrigin() instanceof ElementInfoOrigin) {
			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();

			final ElementInfo<T, C> ei = ((ElementInfoOrigin<T, C, ElementInfo<T, C>>) elementInfo
					.getOrigin()).getSource();

			tis.getElementMappings(ei.getScope().getClazz()).remove(
					ei.getElementName());
		}

	}
}
