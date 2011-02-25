package org.jvnet.jaxb2_commons.xjc.outline;

import com.sun.codemodel.JExpression;

public interface MPropertyAccessorFactory {

	public MPropertyAccessor createPropertyAccessor(JExpression target);

}
