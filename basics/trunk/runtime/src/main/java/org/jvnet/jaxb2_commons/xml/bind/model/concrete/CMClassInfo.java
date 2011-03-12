package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.ClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.PropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;

import com.sun.xml.bind.v2.model.core.ClassInfo;
import com.sun.xml.bind.v2.model.core.PropertyInfo;

public class CMClassInfo<T, C extends T> implements MClassInfo<T, C> {

	private final MClassInfoOrigin origin;
	private final C targetClass;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MClassInfo<T, C> baseTypeInfo;
	private final QName elementName;

	private List<MPropertyInfo<T, C>> properties = new ArrayList<MPropertyInfo<T, C>>();
	private List<MPropertyInfo<T, C>> unmodifiableProperties = Collections
			.unmodifiableList(properties);

	public CMClassInfo(MClassInfoOrigin origin, C targetClass,
			MPackageInfo _package, String localName,
			MClassInfo<T, C> baseTypeInfo, QName elementName) {
		super();
		Validate.notNull(origin);
		Validate.notNull(targetClass);
		Validate.notNull(_package);
		Validate.notNull(localName);
		this.origin = origin;
		this.targetClass = targetClass;
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.baseTypeInfo = baseTypeInfo;
		this.elementName = elementName;
	}

	public MClassInfoOrigin getOrigin() {
		return origin;
	}

	public C getTargetClass() {
		return targetClass;
	}

	public T getTargetType() {
		return targetClass;
	}

	public MElementInfo<T, C> createElementInfo(MTypeInfo<T, C> scope,
			QName substitutionHead) {
		return new CMElementInfo<T, C>(getOrigin().createElementInfoOrigin(),
				getPackageInfo(), getElementName(), scope, this,
				substitutionHead);
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

	public MClassInfo<T, C> getBaseTypeInfo() {
		return baseTypeInfo;
	}

	public List<MPropertyInfo<T, C>> getProperties() {
		return unmodifiableProperties;
	}

	public QName getElementName() {
		return elementName;
	}

	public void addProperty(MPropertyInfo<T, C> propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.add(propertyInfo);
	}

	@SuppressWarnings("unchecked")
	public void removeProperty(MPropertyInfo<T, C> propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.remove(propertyInfo);

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
}
