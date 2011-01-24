package org.jvnet.jaxb2_commons.xjc.model.c;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MClassInfo;
import org.jvnet.jaxb2_commons.xjc.model.MPropertyInfo;

public class CMClassInfo implements MClassInfo {

	private final String name;
	private final MClassInfo baseTypeInfo;

	private final List<MPropertyInfo> properties;
	private final List<MPropertyInfo> unmodifiableProperties;

	public CMClassInfo(String name, MClassInfo baseTypeInfo,
			List<MPropertyInfo> properties) {
		super();
		Validate.notNull(name);
		Validate.noNullElements(properties);
		this.name = name;
		this.baseTypeInfo = baseTypeInfo;
		this.properties = properties;
		this.unmodifiableProperties = Collections.unmodifiableList(properties);
	}

	public String getName() {
		return name;
	}

	public MClassInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	public List<MPropertyInfo> getProperties() {
		return unmodifiableProperties;
	}

}
