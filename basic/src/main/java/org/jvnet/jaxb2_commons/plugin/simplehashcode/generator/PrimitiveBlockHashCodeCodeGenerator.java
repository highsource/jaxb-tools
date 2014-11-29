package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class PrimitiveBlockHashCodeCodeGenerator extends
		BaseHashCodeCodeGenerator {

	public PrimitiveBlockHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public void generate(JBlock block, JType type, JVar left, JVar value) {
		// if (!(left ==null ? right == null : <comparison>))
		// { return false; }
		final JExpression hashCodeValue = hashCodeValue(block, type, value);
		block.assign(left, left.mul(JExpr.lit(31)).plus(hashCodeValue));
	}

	protected abstract JExpression hashCodeValue(JBlock block, JType type,
			JVar value);

}