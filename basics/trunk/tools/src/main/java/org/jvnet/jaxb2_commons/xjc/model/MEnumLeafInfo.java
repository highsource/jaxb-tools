package org.jvnet.jaxb2_commons.xjc.model;

import java.util.List;

public interface MEnumLeafInfo extends MTypeInfo {

	public MTypeInfo getBaseTypeInfo();

	public List<MEnumConstant> getConstants();

}
