package org.jvnet.jaxb2_commons.xjc.model;

import java.util.List;

public interface MClassInfo extends MTypeInfo {

	public MClassInfo getBaseTypeInfo();
	
	public String getName();
	
	public List<MPropertyInfo> getProperties();
}
