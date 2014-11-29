package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class FloatHashCodeCodeGenerator extends
		PrimitiveHashCodeCodeGenerator {
	public FloatHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression transform(JExpression expression) {
		return getCodeModel().ref(Float.class)
				.staticInvoke("floatToIntBits").arg(expression);
	}
}