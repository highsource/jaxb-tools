package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class DoubleEqualsCodeGenerator extends
		PrimitiveEqualsCodeGenerator {
	public DoubleEqualsCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression transform(JExpression expression) {
		return getCodeModel().ref(Double.class)
				.staticInvoke("doubleToLongBits").arg(expression);
	}
}