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

public class CMClassInfo implements MClassInfo {

	private final MClassInfoOrigin origin;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MClassInfo baseTypeInfo;
	private final QName elementName;

	private List<MPropertyInfo> properties = new ArrayList<MPropertyInfo>();
	private List<MPropertyInfo> unmodifiableProperties = Collections
			.unmodifiableList(properties);

	public CMClassInfo(MClassInfoOrigin origin, MPackageInfo _package,
			String localName, MClassInfo baseTypeInfo, QName elementName) {
		super();
		Validate.notNull(origin);
		Validate.notNull(_package);
		Validate.notNull(localName);
		this.origin = origin;
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.baseTypeInfo = baseTypeInfo;
		this.elementName = elementName;
	}

	public MClassInfoOrigin getOrigin() {
		return origin;
	}

	@Override
	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead) {
		return new CMElementInfo(getOrigin().createElementInfoOrigin(),
				getPackageInfo(), getElementName(), scope, this,
				substitutionHead);
	}

	@Override
	public MPackageInfo getPackageInfo() {
		return _package;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public MClassInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	@Override
	public List<MPropertyInfo> getProperties() {
		return unmodifiableProperties;
	}

	@Override
	public QName getElementName() {
		return elementName;
	}

	@Override
	public void addProperty(MPropertyInfo propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.add(propertyInfo);
	}

	@Override
	public void removeProperty(MPropertyInfo propertyInfo) {
		Validate.notNull(propertyInfo);
		this.properties.remove(propertyInfo);

		if (getOrigin() instanceof ClassInfoOrigin
				&& propertyInfo.getOrigin() instanceof PropertyInfoOrigin) {
			ClassInfo ci = (ClassInfo) ((ClassInfoOrigin) getOrigin())
					.getSource();
			PropertyInfo pi = (PropertyInfo) ((PropertyInfoOrigin) propertyInfo
					.getOrigin()).getSource();
			ci.getProperties().remove(pi);
		}
	}

	@Override
	public String toString() {
		return "ClassInfo [" + getName() + "]";
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitClassInfo(this);
	}
}
