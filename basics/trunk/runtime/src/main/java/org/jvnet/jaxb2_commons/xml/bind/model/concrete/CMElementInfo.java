package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

public class CMElementInfo implements MElementInfo {

	private final MElementInfoOrigin origin;

	private final MPackageInfo _package;

	private final QName elementName;

	private final MTypeInfo scope;

	private final MTypeInfo typeInfo;

	private final QName substitutionHead;

	public CMElementInfo(MElementInfoOrigin origin, MPackageInfo _package,
			QName elementName, MTypeInfo scope, MTypeInfo typeInfo,
			QName substitutionHead) {
		super();
		Validate.notNull(origin);
		Validate.notNull(elementName);
		Validate.notNull(_package);
		this.origin = origin;
		this._package = _package;
		this.elementName = elementName;
		this.scope = scope;
		this.typeInfo = typeInfo;
		this.substitutionHead = substitutionHead;
	}

	public MElementInfoOrigin getOrigin() {
		return origin;
	}

	@Override
	public MPackageInfo getPackageInfo() {
		return _package;
	}

	@Override
	public QName getElementName() {
		return elementName;
	}

	@Override
	public MTypeInfo getScope() {
		return scope;
	}

	@Override
	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}

	@Override
	public QName getSubstitutionHead() {
		return substitutionHead;
	}

	@Override
	public String toString() {
		return MessageFormat.format("ElementInfo [{0}: {1}]", getElementName(),
				getTypeInfo());
	}

}
