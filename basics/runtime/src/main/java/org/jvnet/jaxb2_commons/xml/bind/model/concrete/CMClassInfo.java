package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.ClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.PropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;

import org.glassfish.jaxb.core.v2.model.core.ClassInfo;
import org.glassfish.jaxb.core.v2.model.core.PropertyInfo;

public class CMClassInfo<T, C extends T> implements MClassInfo<T, C> {

	private final MClassInfoOrigin origin;
	private CMCustomizations customizations = new CMCustomizations();
	private final C targetType;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MContainer container;
	private final MClassTypeInfo<T, C, ?> baseTypeInfo;
	private final QName elementName;

	private Map<String, MPropertyInfo<T, C>> propertiesMap = new HashMap<String, MPropertyInfo<T, C>>();
	private List<MPropertyInfo<T, C>> properties = new ArrayList<MPropertyInfo<T, C>>();
	private List<MPropertyInfo<T, C>> unmodifiableProperties = Collections
			.unmodifiableList(properties);
	private QName typeName;

	public CMClassInfo(MClassInfoOrigin origin, C targetType,
			MPackageInfo _package, MContainer container, String localName,
			MClassTypeInfo<T, C, ?> baseTypeInfo, QName elementName, QName typeName) {
		super();
		Validate.notNull(origin);
		Validate.notNull(targetType);
		Validate.notNull(_package);
		Validate.notNull(localName);
		this.origin = origin;
		this.targetType = targetType;
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.container = container;
		this.baseTypeInfo = baseTypeInfo;
		this.elementName = elementName;
		this.typeName = typeName;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public MClassInfoOrigin getOrigin() {
		return origin;
	}

	public C getTargetType() {
		return targetType;
	}

	@Override
	public QName getTypeName() {
		return typeName;
	}

	@Override
	public boolean isSimpleType() {
		return false;
	}

	public MElementInfo<T, C> createElementInfo(MClassInfo<T, C> scope,
			QName substitutionHead) {
		return new CMElementInfo<T, C>(getOrigin().createElementInfoOrigin(),
				getPackageInfo(), getContainer(), getLocalName(),
				getElementName(), scope, this, substitutionHead, null, null);
	}

	public MPackageInfo getPackageInfo() {
		return _package;
	}

	public String getName() {
		return name;
	}

	public String getLocalName() {
		return localName;
	}

	public MContainer getContainer() {
		return container;
	}

	public String getContainerLocalName(String delimiter) {
		final String localName = getLocalName();
		if (localName == null) {
			return null;
		} else {
			final MContainer container = getContainer();
			if (container == null) {
				return localName;
			} else {
				final String containerLocalName = container
						.getContainerLocalName(delimiter);
				return containerLocalName == null ? localName
						: containerLocalName + delimiter + localName;
			}
		}
	}

	public MClassTypeInfo<T, C, ?> getBaseTypeInfo() {
		return baseTypeInfo;
	}

	public List<MPropertyInfo<T, C>> getProperties() {
		return unmodifiableProperties;
	}

	@Override
	public MPropertyInfo<T, C> getProperty(String privateName) {
		return this.propertiesMap.get(privateName);
	}

	public QName getElementName() {
		return elementName;
	}

	public void addProperty(MPropertyInfo<T, C> propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.add(propertyInfo);
		this.propertiesMap.put(propertyInfo.getPrivateName(), propertyInfo);
	}

	@SuppressWarnings("unchecked")
	public void removeProperty(MPropertyInfo<T, C> propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.remove(propertyInfo);
		this.propertiesMap.remove(propertyInfo.getPrivateName());

		if (getOrigin() instanceof ClassInfoOrigin
				&& propertyInfo.getOrigin() instanceof PropertyInfoOrigin) {
			ClassInfo<T, C> ci = (ClassInfo<T, C>) ((ClassInfoOrigin) getOrigin())
					.getSource();
			PropertyInfo<T, C> pi = (PropertyInfo<T, C>) ((PropertyInfoOrigin) propertyInfo
					.getOrigin()).getSource();
			ci.getProperties().remove(pi);
		}
	}

	public String toString() {
		return "ClassInfo [" + getName() + "]";
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitClassInfo(this);
	}

	@Override
	public <V> V acceptClassTypeInfoVisitor(
			MClassTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitClassInfo(this);
	}
}
