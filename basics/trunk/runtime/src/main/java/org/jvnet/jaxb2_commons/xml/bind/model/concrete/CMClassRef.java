package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMClassRef<T, C extends T> implements MClassRef<T, C> {

	private CMCustomizations customizations = new CMCustomizations();
	private final C targetClass;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MContainer container;

	public CMClassRef(/*MClassInfoOrigin origin, */C targetClass,
			MPackageInfo _package, MContainer container, String localName) {
		super();
//		Validate.notNull(origin);
		Validate.notNull(targetClass);
		Validate.notNull(_package);
		Validate.notNull(localName);
		// this.origin = origin;
		this.targetClass = targetClass;
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.container = container;
	}

	public String getName() {
		return name;
	}

	public String getLocalName() {
		return localName;
	}

	public T getTargetType() {
		return targetClass;
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitClassRef(this);
	}

	public MCustomizations getCustomizations() {
		return customizations;
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

	public C getTargetClass() {
		return targetClass;
	}

}
