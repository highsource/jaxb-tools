package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class DoubleHashCodeCodeGenerator extends
		PrimitiveHashCodeCodeGenerator {
	public DoubleHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression transform(JExpression expression) {
		return getCodeModel().ref(Double.class)
				.staticInvoke("doubleToLongBits").arg(expression);
	}
}