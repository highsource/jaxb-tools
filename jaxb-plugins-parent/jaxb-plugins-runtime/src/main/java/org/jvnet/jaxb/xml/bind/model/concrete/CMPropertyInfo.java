package org.jvnet.jaxb.xml.bind.model.concrete;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MCustomizable;
import org.jvnet.jaxb.xml.bind.model.MCustomizations;
import org.jvnet.jaxb.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMPropertyInfo<T, C extends T> implements
		MPropertyInfo<T, C>, MCustomizable {

	private CMCustomizations customizations = new CMCustomizations();
	private MPropertyInfoOrigin origin;
	private MClassInfo<T, C> classInfo;

	private final String privateName;

	private final boolean collection;

	private final boolean required;

	public CMPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			boolean required) {
		Validate.notNull(origin);
		Validate.notNull(classInfo);
		Validate.notNull(privateName);
		this.origin = origin;
		this.classInfo = classInfo;
		this.privateName = privateName;
		this.collection = collection;
		this.required = required;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public MPropertyInfoOrigin getOrigin() {
		return origin;
	}

	public MClassInfo<T, C> getClassInfo() {
		return classInfo;
	}

	public String getPrivateName() {
		return privateName;
	}

	public String getPublicName() {
		// TODO
		return this.getPrivateName();
	}

	public boolean isCollection() {
		return collection;
	}

	public boolean isRequired() {
		return required;
	}

}
