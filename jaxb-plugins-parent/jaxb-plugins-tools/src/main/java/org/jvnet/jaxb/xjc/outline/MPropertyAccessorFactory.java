package org.jvnet.jaxb.xjc.outline;

import com.sun.codemodel.JExpression;

public interface MPropertyAccessorFactory {

	public MPropertyAccessor createPropertyAccessor(JExpression target);

}
