package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class CastToIntHashCodeCodeGenerator extends
		ValueBasedHashCodeCodeGenerator {

	public CastToIntHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	public JExpression generateHashCode(JType type, JVar value) {
		return JExpr.cast(getCodeModel().INT, value);
	}
}