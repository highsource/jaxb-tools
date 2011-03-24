package org.jvnet.jaxb2_commons.xjc.outline;

import com.sun.codemodel.JDefinedClass;

public interface MObjectFactoryOutline extends MChildOutline, MPackagedOutline {

	public JDefinedClass getCode();
}
