package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

public class CMModel<T, C extends T> implements MModelInfo<T, C> {

	private final MModelInfoOrigin origin;

	private final CMCustomizations customizations = new CMCustomizations();

	private final Collection<MBuiltinLeafInfo<T, C>> builtinLeafInfos = new ArrayList<MBuiltinLeafInfo<T, C>>();
	private final Collection<MBuiltinLeafInfo<T, C>> unmodifiableBuiltinLeafInfos = Collections
			.unmodifiableCollection(builtinLeafInfos);
	private final Map<QName, MBuiltinLeafInfo<T, C>> builtinLeafInfosMap = new HashMap<QName, MBuiltinLeafInfo<T, C>>();
	private final Map<QName, MBuiltinLeafInfo<T, C>> unmodifiableBuiltinLeafInfosMap = Collections
			.unmodifiableMap(builtinLeafInfosMap);

	private final Map<String, MClassInfo<T, C>> classInfosMap = new LinkedHashMap<String, MClassInfo<T, C>>();
	private final Collection<MClassInfo<T, C>> classInfos = new ArrayList<MClassInfo<T, C>>();
	private final Collection<MClassInfo<T, C>> unmodifiableClassInfos = Collections
			.unmodifiableCollection(classInfos);

	private final Collection<MEnumLeafInfo<T, C>> enumLeafInfos = new ArrayList<MEnumLeafInfo<T, C>>();
	private final Collection<MEnumLeafInfo<T, C>> unmodifiableEnumLeafInfos = Collections
			.unmodifiableCollection(enumLeafInfos);

	private final Map<QName, MTypeInfo<T, C>> typeInfosMap = new LinkedHashMap<QName, MTypeInfo<T, C>>();
	private final Collection<MTypeInfo<T, C>> typeInfos = new ArrayList<MTypeInfo<T, C>>();
	private final Collection<MTypeInfo<T, C>> unmodifiableTypeInfos = Collections
			.unmodifiableCollection(typeInfos);

	private final Collection<MElementInfo<T, C>> elementInfos = new ArrayList<MElementInfo<T, C>>();
	private final Map<QName, MElementInfo<T, C>> globalElementInfosMap = new LinkedHashMap<QName, MElementInfo<T, C>>();
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

	@Override
	public MClassInfo<T, C> getClassInfo(String name) {
		return this.classInfosMap.get(name);
	}

	public Collection<MEnumLeafInfo<T, C>> getEnumLeafInfos() {
		return unmodifiableEnumLeafInfos;
	}

	public Collection<MElementInfo<T, C>> getElementInfos() {
		return unmodifiableElementInfos;
	}

	@Override
	public MElementInfo<T, C> getGlobalElementInfo(QName elementName) {
		return this.globalElementInfosMap.get(elementName);
	}

	public Map<QName, MElementInfo<T, C>> getElementInfosMap() {
		return unmodifiableElementInfosMap;
	}

	public Collection<MTypeInfo<T, C>> getTypeInfos() {
		return unmodifiableTypeInfos;
	}

	@Override
	public MTypeInfo<T, C> getTypeInfo(QName typeName) {
		return this.typeInfosMap.get(typeName);
	}

	public MBuiltinLeafInfo<T, C> getBuiltinLeafInfo(QName name) {
		Validate.notNull(name);
		return this.unmodifiableBuiltinLeafInfosMap.get(name);
	}

	public void addBuiltinLeafInfo(MBuiltinLeafInfo<T, C> builtinLeafInfo) {
		Validate.notNull(builtinLeafInfo);
		this.builtinLeafInfos.add(builtinLeafInfo);
		this.typeInfos.add(builtinLeafInfo);
		final QName typeName = builtinLeafInfo.getTypeName();
		if (typeName != null) {
			this.typeInfosMap.put(typeName, builtinLeafInfo);
			this.builtinLeafInfosMap.put(typeName, builtinLeafInfo);
		}
	}

	public void addEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.add(enumLeafInfo);
		this.typeInfos.add(enumLeafInfo);
		final QName typeName = enumLeafInfo.getTypeName();
		if (typeName != null) {
			this.typeInfosMap.put(typeName, enumLeafInfo);
		}

		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = enumLeafInfo
					.createElementInfo(null, null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
			if (elementInfo.getScope() == null) {
				this.globalElementInfosMap.put(elementName, elementInfo);
			}
		}

	}

	public void removeEnumLeafInfo(MEnumLeafInfo<T, C> enumLeafInfo) {
		Validate.notNull(enumLeafInfo);
		this.enumLeafInfos.remove(enumLeafInfo);
		this.typeInfos.remove(enumLeafInfo);
		final QName typeName = enumLeafInfo.getTypeName();
		if (typeName != null) {
			this.typeInfosMap.remove(typeName);
		}
		final QName elementName = enumLeafInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = this.elementInfosMap
					.remove(elementName);
			this.globalElementInfosMap.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		// TODO Not very good
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& enumLeafInfo.getOrigin() instanceof EnumLeafInfoOrigin) {

			@SuppressWarnings("unchecked")
			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();

			@SuppressWarnings("unchecked")
			final EnumLeafInfo<T, C> eli = ((EnumLeafInfoOrigin<T, C, EnumLeafInfo<T, C>>) enumLeafInfo
					.getOrigin()).getSource();
			tis.enums().remove(eli.getClazz());
		}
	}

	public void addClassInfo(MClassInfo<T, C> classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.add(classInfo);
		this.classInfosMap.put(classInfo.getName(), classInfo);
		this.typeInfos.add(classInfo);

		final QName typeName = classInfo.getTypeName();
		if (typeName != null) {
			this.typeInfosMap.put(typeName, classInfo);
		}

		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			// TODO why null, null?
			final MElementInfo<T, C> elementInfo = classInfo.createElementInfo(
					null, null);
			this.elementInfos.add(elementInfo);
			this.elementInfosMap.put(elementName, elementInfo);
			if (elementInfo.getScope() == null) {
				this.globalElementInfosMap.put(elementName, elementInfo);
			}
		}
	}

	public void removeClassInfo(MClassInfo<T, C> classInfo) {
		Validate.notNull(classInfo);
		this.classInfos.remove(classInfo);
		this.classInfosMap.remove(classInfo.getName());
		this.typeInfos.remove(classInfo);
		final QName typeName = classInfo.getTypeName();
		if (typeName != null) {
			this.typeInfosMap.remove(typeName);
		}
		final QName elementName = classInfo.getElementName();
		if (elementName != null) {
			final MElementInfo<T, C> elementInfo = this.elementInfosMap
					.remove(elementName);
			this.globalElementInfosMap.remove(elementName);
			if (elementInfo != null) {
				this.elementInfos.remove(elementInfo);
			}
		}
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& classInfo.getOrigin() instanceof ClassInfoOrigin) {
			@SuppressWarnings("unchecked")
			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();
			@SuppressWarnings("unchecked")
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
		if (elementInfo.getScope() == null) {
			this.globalElementInfosMap.put(elementInfo.getElementName(),
					elementInfo);
		}
	}

	public void removeElementInfo(MElementInfo<T, C> elementInfo) {
		Validate.notNull(elementInfo);
		Validate.notNull(elementInfo.getElementName());
		this.elementInfos.remove(elementInfo);
		this.elementInfosMap.remove(elementInfo.getElementName());
		this.globalElementInfosMap.remove(elementInfo.getElementName());
		// TODO Not very good
		if (getOrigin() instanceof TypeInfoSetOrigin
				&& elementInfo.getOrigin() instanceof ElementInfoOrigin) {
			@SuppressWarnings("unchecked")
			final TypeInfoSet<T, C, ?, ?> tis = ((TypeInfoSetOrigin<T, C, TypeInfoSet<T, C, ?, ?>>) getOrigin())
					.getSource();

			@SuppressWarnings("unchecked")
			final ElementInfo<T, C> ei = ((ElementInfoOrigin<T, C, ElementInfo<T, C>>) elementInfo
					.getOrigin()).getSource();

			tis.getElementMappings(ei.getScope().getClazz()).remove(
					ei.getElementName());
		}

	}
}
