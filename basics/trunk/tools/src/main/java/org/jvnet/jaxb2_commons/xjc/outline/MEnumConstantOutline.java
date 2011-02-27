package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

import com.sun.codemodel.JEnumConstant;

public interface MEnumConstantOutline extends MTargeted<MEnumConstantInfo> {

	public MEnumOutline getEnumOutline();

	public MEnumConstantInfo getTarget();

	public JEnumConstant getCode();

}
