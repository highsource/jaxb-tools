package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class ObjectEqualsCodeGenerator extends
		BasicEqualsCodeGenerator {

	public ObjectEqualsCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression comparison(JExpression left, JExpression right) {
		// left.equals(right)
		return left.invoke("equals").arg(right);
	}
}