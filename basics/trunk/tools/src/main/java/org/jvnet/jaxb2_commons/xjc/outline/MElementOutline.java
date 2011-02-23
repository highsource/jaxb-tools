package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;

import com.sun.codemodel.JDefinedClass;

public interface MElementOutline extends MChildOutline, MPackagedOutline,
		MTargeted<MElementInfo> {

	public MElementInfo getTarget();

	public JDefinedClass getCode();
}
