package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class IntHashCodeCodeGenerator extends
		PrimitiveHashCodeCodeGenerator {
	public IntHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression hashCodeValue(JType type, JVar value) {
		return value;
	}
}