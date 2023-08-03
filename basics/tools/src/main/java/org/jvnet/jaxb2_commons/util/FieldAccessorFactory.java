package org.jvnet.jaxb2_commons.util;

import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.FieldOutline;

public interface FieldAccessorFactory {

	public FieldAccessorEx createFieldAccessor(FieldOutline fieldOutline,
			JExpression targetObject);
}
