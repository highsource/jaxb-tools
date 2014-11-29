package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class ObjectHashCodeCodeGenerator extends
		BasicHashCodeCodeGenerator {

	public ObjectHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression comparison(JExpression left, JExpression right) {
		// left.hashCode(right)
		return left.invoke("hashCode").arg(right);
	}
}