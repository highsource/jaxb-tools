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
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModel;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;

public class CMModel implements MModel {

	private final Collection<MBuiltinLeafInfo> builtinLeafInfos;
	private final Map<QName, MBuiltinLeafInfo> builtinLeafInfosMap = new HashMap<QName, MBuiltinLeafInfo>();

	private final Collection<MClassInfo> classInfos;

	private final Collection<MEnumLeafInfo> enumLeafInfos;

	private final Collection<MTypeInfo> typeInfos;

	private final Collection<MElementInfo> elementInfos;

	private final Map<QName, MElementInfo> elementInfosMap;

	public CMModel(Collection<MBuiltinLeafInfo> builtinLeafInfos,
			Collection<MClassInfo> classInfos,
			Collection<MEnumLeafInfo> enumLeafInfos,
			Collection<MElementInfo> elementInfos) {
		super();
		Validate.noNullElements(builtinLeafInfos);
		Validate.noNullElements(classInfos);
		Validate.noNullElements(enumLeafInfos);
		Validate.noNullElements(elementInfos);
		this.builtinLeafInfos = Collections
				.unmodifiableCollection(builtinLeafInfos);
		this.classInfos = Collections.unmodifiableCollection(classInfos);
		this.enumLeafInfos = Collections.unmodifiableCollection(enumLeafInfos);

		{
			for (MBuiltinLeafInfo builtinLeafInfo : this.builtinLeafInfos) {
				this.builtinLeafInfosMap.put(builtinLeafInfo.getTypeName(),
						builtinLeafInfo);
			}
		}

		// Initialize type infos
		{
			final Collection<MTypeInfo> typeInfos = new ArrayList<MTypeInfo>(

			this.builtinLeafInfos.size() + this.classInfos.size()
					+ this.enumLeafInfos.size());

			typeInfos.addAll(this.builtinLeafInfos);
			typeInfos.addAll(this.classInfos);
			typeInfos.addAll(this.enumLeafInfos);

			this.typeInfos = Collections.unmodifiableCollection(typeInfos);
		}

		{
			final Collection<MElementInfo> allElementInfos = new ArrayList<MElementInfo>(
					elementInfos.size());
			final Map<QName, MElementInfo> allElementInfosMap = new HashMap<QName, MElementInfo>(
					elementInfos.size());

			allElementInfos.addAll(elementInfos);

			for (MElementInfo elementInfo : elementInfos) {
				allElementInfosMap.put(elementInfo.getElementName(),
						elementInfo);
			}

			for (MClassInfo classInfo : classInfos) {
				QName elementName = classInfo.getElementName();
				if (elementName != null) {
					final MElementInfo elementInfo = new CMElementInfo(
							classInfo.getPackage(), elementName, null,
							classInfo, null);
					allElementInfos.add(elementInfo);
					allElementInfosMap.put(elementName, elementInfo);
				}
			}
			for (MEnumLeafInfo enumLeafInfo : enumLeafInfos) {
				QName elementName = enumLeafInfo.getElementName();
				if (elementName != null) {
					final MElementInfo elementInfo = new CMElementInfo(
							enumLeafInfo.getPackage(), elementName, null,
							enumLeafInfo, null);
					allElementInfos.add(elementInfo);
					allElementInfosMap.put(elementName, elementInfo);
				}
			}
			this.elementInfos = Collections
					.unmodifiableCollection(allElementInfos);

			this.elementInfosMap = Collections
					.unmodifiableMap(allElementInfosMap);
		}

	}

	@Override
	public Collection<MBuiltinLeafInfo> getBuiltinLeafInfos() {
		return builtinLeafInfos;
	}

	@Override
	public Collection<MClassInfo> getClassInfos() {
		return classInfos;
	}

	@Override
	public Collection<MEnumLeafInfo> getEnumLeafInfos() {
		return enumLeafInfos;
	}

	@Override
	public Collection<MElementInfo> getElementInfos() {
		return elementInfos;
	}

	@Override
	public Map<QName, MElementInfo> getElementInfosMap() {
		return elementInfosMap;
	}

	@Override
	public Collection<MTypeInfo> getTypeInfos() {
		return typeInfos;
	}

	@Override
	public MBuiltinLeafInfo getBuiltinLeafInfo(QName name) {
		Validate.notNull(name);
		return this.builtinLeafInfosMap.get(name);
	}

}
