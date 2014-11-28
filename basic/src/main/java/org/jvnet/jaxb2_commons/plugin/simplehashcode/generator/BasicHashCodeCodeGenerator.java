package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;

public abstract class BasicHashCodeCodeGenerator implements
		HashCodeCodeGenerator {

	private final JCodeModel codeModel;

	public BasicHashCodeCodeGenerator(JCodeModel codeModel) {
		this.codeModel = Validate.notNull(codeModel);
	}

	@Override
	public void generate(JBlock block, JType type, JExpression left,
			JExpression right) {
		// if (!(left ==null ? right == null : <comparison>))
		// { return false; }
		final JExpression comparison = comparison(left, right);
		block._if(
				JOp.cond(left.eq(JExpr._null()), right.eq(JExpr._null()),
						comparison).not())._then()._return(JExpr.FALSE);
	}

	public abstract JExpression comparison(JExpression left,
			JExpression right);
}