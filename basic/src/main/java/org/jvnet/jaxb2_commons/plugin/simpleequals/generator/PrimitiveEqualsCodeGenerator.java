package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;

public class PrimitiveEqualsCodeGenerator extends
		BaseEqualsCodeGenerator {

	public PrimitiveEqualsCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public void generate(JBlock block, JType type, JExpression left,
			JExpression right) {
		// if (!(left ==null ? right == null : <comparison>))
		// { return false; }
		final JExpression comparison = comparison(left, right);
		block._if(comparison.not())._then()._return(JExpr.FALSE);
	}

	public JExpression comparison(JExpression left, JExpression right) {
		return transform(left).eq(transform(right));
	}

	public JExpression transform(JExpression expression) {
		return expression;
	}
}