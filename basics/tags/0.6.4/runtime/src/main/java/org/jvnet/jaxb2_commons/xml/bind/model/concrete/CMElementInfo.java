package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

public class CMElementInfo<T, C> implements MElementInfo<T, C> {

	private final MElementInfoOrigin origin;

	private final MPackageInfo _package;

	private final QName elementName;

	private final MTypeInfo<T, C> scope;

	private final MTypeInfo<T, C> typeInfo;

	private final QName substitutionHead;

	public CMElementInfo(MElementInfoOrigin origin, MPackageInfo _package,
			QName elementName, MTypeInfo<T, C> scope, MTypeInfo<T, C> typeInfo,
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

	public MPackageInfo getPackageInfo() {
		return _package;
	}

	public QName getElementName() {
		return elementName;
	}

	public MTypeInfo<T, C> getScope() {
		return scope;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

	public QName getSubstitutionHead() {
		return substitutionHead;
	}

	public String toString() {
		return MessageFormat.format("ElementInfo [{0}: {1}]", getElementName(),
				getTypeInfo());
	}

}
