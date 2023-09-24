package org.jvnet.jaxb.util;

import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.FieldOutline;

public interface FieldAccessorFactory {

	public FieldAccessorEx createFieldAccessor(FieldOutline fieldOutline,
			JExpression targetObject);
}
