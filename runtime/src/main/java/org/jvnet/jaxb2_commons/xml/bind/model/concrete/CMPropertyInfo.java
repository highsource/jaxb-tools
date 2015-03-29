package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizable;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMPropertyInfo<T, C extends T> implements MPropertyInfo<T, C>,
		MCustomizable {

	private CMCustomizations customizations = new CMCustomizations();
	private MPropertyInfoOrigin origin;
	private MClassInfo<T, C> classInfo;

	private final String privateName;

	private final boolean collection;

	public CMPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection) {
		Validate.notNull(origin);
		Validate.notNull(classInfo);
		Validate.notNull(privateName);
		this.origin = origin;
		this.classInfo = classInfo;
		this.privateName = privateName;
		this.collection = collection;
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

}
