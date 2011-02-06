package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstant;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackage;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMEnumLeafInfo implements MEnumLeafInfo {

	private final MPackage _package;
	private final String name;
	private final String localName;
	private final MTypeInfo baseTypeInfo;
	private final List<MEnumConstant> unmodifiableConstants;
	private final QName elementName;

	public CMEnumLeafInfo(MPackage _package, String localName,
			MTypeInfo baseTypeInfo, List<MEnumConstant> constants,

			QName elementName) {
		Validate.notNull(_package);
		Validate.notNull(localName);
		Validate.notNull(baseTypeInfo);
		Validate.notEmpty(constants);
		this._package = _package;
		this.localName = localName;
		this.name = _package.getPackagedName(localName);
		this.baseTypeInfo = baseTypeInfo;
		this.unmodifiableConstants = Collections.unmodifiableList(constants);
		// May be null
		this.elementName = elementName;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public MPackage getPackage() {
		return _package;
	}

	@Override
	public MTypeInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	@Override
	public List<MEnumConstant> getConstants() {
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
