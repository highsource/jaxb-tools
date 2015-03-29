package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

public class CMElementInfo<T, C extends T> implements MElementInfo<T, C> {

	private final MElementInfoOrigin origin;

	private final MPackageInfo _package;

	private final MContainer container;

	private final String localName;

	private final QName elementName;

	private final MTypeInfo<T, C> scope;

	private final MTypeInfo<T, C> typeInfo;

	private final QName substitutionHead;

	public CMElementInfo(MElementInfoOrigin origin, MPackageInfo _package,
			MContainer container, String localName, QName elementName,
			MTypeInfo<T, C> scope, MTypeInfo<T, C> typeInfo,
			QName substitutionHead) {
		super();
		Validate.notNull(origin);
		Validate.notNull(elementName);
		Validate.notNull(_package);
		this.origin = origin;
		this._package = _package;
		this.container = container;
		this.localName = localName;
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

	public MContainer getContainer() {
		return container;
	}

	public String getLocalName() {
		return localName;
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
