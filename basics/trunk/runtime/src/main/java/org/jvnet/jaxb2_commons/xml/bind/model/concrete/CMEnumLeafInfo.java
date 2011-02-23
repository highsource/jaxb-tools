package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

import com.sun.xml.bind.v2.model.core.EnumLeafInfo;

public class CMEnumLeafInfo implements MEnumLeafInfo {

	private final EnumLeafInfo<?, ?> enumLeafInfo;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MTypeInfo baseTypeInfo;
	private final List<MEnumConstantInfo> unmodifiableConstants;
	private final QName elementName;

	public CMEnumLeafInfo(EnumLeafInfo<?, ?> enumLeafInfo, MPackageInfo _package,
			String localName, MTypeInfo baseTypeInfo,
			List<MEnumConstantInfo> constants,

			QName elementName) {

		Validate.notNull(enumLeafInfo);
		Validate.notNull(_package);
		Validate.notNull(localName);
		Validate.notNull(baseTypeInfo);
		Validate.notEmpty(constants);
		this.enumLeafInfo = enumLeafInfo;
		this._package = _package;
		this.localName = localName;
		this.name = _package.getPackagedName(localName);
		this.baseTypeInfo = baseTypeInfo;
		this.unmodifiableConstants = Collections.unmodifiableList(constants);
		// May be null
		this.elementName = elementName;
	}

	public EnumLeafInfo<?, ?> getEnumLeafInfo() {
		return enumLeafInfo;
	}

	@Override
	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead) {
		return new CMElementInfo(getEnumLeafInfo(), getPackage(),
				getElementName(), scope, this, substitutionHead);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public MPackageInfo getPackage() {
		return _package;
	}

	@Override
	public MTypeInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	@Override
	public List<MEnumConstantInfo> getConstants() {
		return unmodifiableConstants;
	}

	@Override
	public QName getElementName() {
		return elementName;
	}

	@Override
	public String toString() {
		return MessageFormat.format("EnumInfo [{0}]", getBaseTypeInfo());
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitEnumLeafInfo(this);
	}

}
