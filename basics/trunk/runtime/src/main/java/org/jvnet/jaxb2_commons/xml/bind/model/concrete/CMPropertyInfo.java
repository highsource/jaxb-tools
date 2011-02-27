package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMPropertyInfo implements MPropertyInfo {

	private MPropertyInfoOrigin origin;
	private MClassInfo classInfo;

	private final String privateName;

	private final boolean collection;

	public CMPropertyInfo(MPropertyInfoOrigin origin, MClassInfo classInfo,
			String privateName, boolean collection) {
		Validate.notNull(origin);
		Validate.notNull(classInfo);
		Validate.notNull(privateName);
		this.origin = origin;
		this.classInfo = classInfo;
		this.privateName = privateName;
		this.collection = collection;
	}

	public MPropertyInfoOrigin getOrigin() {
		return origin;
	}

	public MClassInfo getClassInfo() {
		return classInfo;
	}

	public String getPrivateName() {
		return privateName;
	}

	public boolean isCollection() {
		return collection;
	}

}
