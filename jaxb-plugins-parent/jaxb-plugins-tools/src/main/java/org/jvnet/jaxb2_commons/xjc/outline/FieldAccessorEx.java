package org.jvnet.jaxb2_commons.xjc.outline;

import com.sun.codemodel.JType;
import com.sun.tools.xjc.outline.FieldAccessor;

public interface FieldAccessorEx extends FieldAccessor {

	public JType getType();

	public boolean isConstant();

	public boolean isVirtual();

	public boolean isAlwaysSet();
}
