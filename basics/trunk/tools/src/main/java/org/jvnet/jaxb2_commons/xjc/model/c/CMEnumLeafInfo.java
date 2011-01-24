package org.jvnet.jaxb2_commons.xjc.model.c;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MEnumConstant;
import org.jvnet.jaxb2_commons.xjc.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;

public class CMEnumLeafInfo implements MEnumLeafInfo {

	private final MTypeInfo baseTypeInfo;
	private final List<MEnumConstant> constants;
	private final List<MEnumConstant> unmodifiableConstants;

	public CMEnumLeafInfo(MTypeInfo baseTypeInfo, List<MEnumConstant> constants) {
		Validate.notNull(baseTypeInfo);
		Validate.notEmpty(constants);
		this.baseTypeInfo = baseTypeInfo;
		this.constants = constants;
		this.unmodifiableConstants = Collections.unmodifiableList(constants);
	}

	public MTypeInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	public List<MEnumConstant> getConstants() {
		return unmodifiableConstants;
	}

}
