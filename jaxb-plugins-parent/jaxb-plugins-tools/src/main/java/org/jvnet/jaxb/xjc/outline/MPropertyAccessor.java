package org.jvnet.jaxb.xjc.outline;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public interface MPropertyAccessor {

	public void get(JBlock block, JVar variable);

	public void set(JBlock block, String uniqueName, JExpression value);

	public void unset(JBlock body);

	public JExpression isSet();

	public JType getType();

	public boolean isConstant();

	public boolean isVirtual();
}
