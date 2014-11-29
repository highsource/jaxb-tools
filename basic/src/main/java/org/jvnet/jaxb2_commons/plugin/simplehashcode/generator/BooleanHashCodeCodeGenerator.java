package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class BooleanHashCodeCodeGenerator extends
		PrimitiveHashCodeCodeGenerator {
	public BooleanHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression hashCodeValue(JType type, JVar value) {
		// (<value>? 1231 : 1237);
		return JOp.cond(value, JExpr.lit(1231), JExpr.lit(1237));
	}
}