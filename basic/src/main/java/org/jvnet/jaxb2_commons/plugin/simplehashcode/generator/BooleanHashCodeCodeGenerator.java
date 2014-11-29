package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class BooleanHashCodeCodeGenerator extends
ValueBasedHashCodeCodeGenerator {

	public BooleanHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	public JExpression valueHashCode(JType type, JVar value) {
		return JOp.cond(value, JExpr.lit(0), JExpr.lit(1));
	}
}