package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizable;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.EnumConstantOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.EnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;

import org.glassfish.jaxb.core.v2.model.core.EnumConstant;
import org.glassfish.jaxb.core.v2.model.core.EnumLeafInfo;

public class CMEnumLeafInfo<T, C extends T> implements MEnumLeafInfo<T, C>,
		MCustomizable {

	private final MEnumLeafInfoOrigin origin;
	private final CMCustomizations customizations = new CMCustomizations();
	private final C targetClass;
	private final MPackageInfo _package;
	private final MContainer container;
	private final String name;
	private final String localName;
	private final MTypeInfo<T, C> baseTypeInfo;
	private final List<MEnumConstantInfo<T, C>> constants = new ArrayList<MEnumConstantInfo<T, C>>();
	private final List<MEnumConstantInfo<T, C>> _constants = Collections
			.unmodifiableList(constants);
	private final QName elementName;
	private final QName typeName;

	public CMEnumLeafInfo(MEnumLeafInfoOrigin origin, C targetClass,
			MPackageInfo _package, MContainer container, String localName,
			MTypeInfo<T, C> baseTypeInfo, QName elementName, QName typeName) {

		Validate.notNull(origin);
		Validate.notNull(targetClass);
		Validate.notNull(_package);
		Validate.notNull(localName);
		Validate.notNull(baseTypeInfo);
		this.origin = origin;
		this.targetClass = targetClass;
		this._package = _package;
		this.container = container;
		this.localName = localName;
		this.name = _package.getPackagedName(localName);
		this.baseTypeInfo = baseTypeInfo;
		// May be null
		this.elementName = elementName;
		this.typeName = typeName;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public MEnumLeafInfoOrigin getOrigin() {
		return origin;
	}

	public C getTargetClass() {
		return targetClass;
	}

	public T getTargetType() {
		return targetClass;
	}

	@Override
	public QName getTypeName() {
		return this.typeName;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}

	public MElementInfo<T, C> createElementInfo(MClassInfo<T, C> scope,
			QName substitutionHead) {
		return new CMElementInfo<T, C>(getOrigin().createElementInfoOrigin(),
				getPackageInfo(), getContainer(), getLocalName(),
				getElementName(), scope, this, substitutionHead, null, null);
	}

	public String getName() {
		return name;
	}

	public String getLocalName() {
		return localName;
	}

	public MPackageInfo getPackageInfo() {
		return _package;
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

	public MTypeInfo<T, C> getBaseTypeInfo() {
		return baseTypeInfo;
	}

	public List<MEnumConstantInfo<T, C>> getConstants() {
		return _constants;
	}

	public void addEnumConstantInfo(MEnumConstantInfo<T, C> enumConstantInfo) {
		Validate.notNull(enumConstantInfo);
		this.constants.add(enumConstantInfo);
	}

	@SuppressWarnings("unchecked")
	public void removeEnumConstantInfo(MEnumConstantInfo<T, C> enumConstantInfo) {
		Validate.notNull(enumConstantInfo);

		if (getOrigin() instanceof EnumLeafInfoOrigin
				&& enumConstantInfo.getOrigin() instanceof EnumConstantOrigin) {
			// TODO
			EnumLeafInfo<T, C> eli = (EnumLeafInfo<T, C>) ((EnumLeafInfoOrigin) getOrigin())
					.getSource();
			EnumConstant<T, C> ec = (EnumConstant<T, C>) ((EnumConstantOrigin) enumConstantInfo
					.getOrigin()).getSource();

			Iterator iterator = eli.getConstants().iterator();

			while (iterator.hasNext()) {
				if (iterator.next() == ec) {
					iterator.remove();
				}
			}
		}
		// TODO Auto-generated method stub

	}

	public QName getElementName() {
		return elementName;
	}

	@Override
	public String toString() {
		return MessageFormat.format("EnumInfo [{0}]", getBaseTypeInfo());
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitEnumLeafInfo(this);
	}

}
