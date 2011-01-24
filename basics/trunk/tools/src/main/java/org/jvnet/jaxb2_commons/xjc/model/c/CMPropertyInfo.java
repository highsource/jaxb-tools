package org.jvnet.jaxb2_commons.xjc.model.c;

import org.jvnet.jaxb2_commons.xjc.model.MPropertyInfo;

public abstract class CMPropertyInfo implements MPropertyInfo {

	private final String privateName;

	private final boolean collection;

	public CMPropertyInfo(String privateName, boolean collection) {
		super();
		this.privateName = privateName;
		this.collection = collection;
	}

	public String getPrivateName() {
		return privateName;
	}

	public boolean isCollection() {
		return collection;
	}

}
