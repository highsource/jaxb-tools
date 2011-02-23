package org.jvnet.jaxb2_commons.xjc.outline;

import java.util.List;

import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;

import com.sun.codemodel.JDefinedClass;

public interface MEnumOutline extends MChildOutline, MPackagedOutline,
		MTargeted<MEnumLeafInfo> {

	public MEnumLeafInfo getTarget();

	public List<MEnumConstantOutline> getEnumConstantOutlines();

	public JDefinedClass getCode();
}
