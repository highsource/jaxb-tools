package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackage;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMClassInfo implements MClassInfo {

	private final MPackage _package;
	private final String name;
	private final String localName;
	private final MClassInfo baseTypeInfo;
	private final QName elementName;

	private List<MPropertyInfo> properties = new ArrayList<MPropertyInfo>();
	private List<MPropertyInfo> unmodifiableProperties = Collections
			.unmodifiableList(properties);

	public CMClassInfo(MPackage _package, String localName,
			MClassInfo baseTypeInfo, QName elementName) {
		super();
		Validate.notNull(_package);
		Validate.notNull(localName);
		// Validate.noNullElements(properties);
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.baseTypeInfo = baseTypeInfo;
		// this.properties = properties;
		// this.unmodifiableProperties =
		// Collections.unmodifiableList(properties);
		this.elementName = elementName;
	}

	@Override
	public MPackage getPackage() {
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
	public String toString() {
		return "ClassInfo [" + getName() + "]";
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitClassInfo(this);
	}
}
