package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class FloatEqualsCodeGenerator extends
		PrimitiveEqualsCodeGenerator {
	public FloatEqualsCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression transform(JExpression expression) {
		return getCodeModel().ref(Float.class)
				.staticInvoke("floatToIntBits").arg(expression);
	}
}