package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class BasicHashCodeCodeGenerator extends
		BaseHashCodeCodeGenerator {

	public BasicHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public void generate(JBlock block, JType type, JVar hashCode, JVar value) {
		// hashCode = 31 * hashCode + (value==null ? 0 : <valueHashCode>);
		final JExpression valueHashCode = valueHashCode(type, hashCode);
		block.assign(hashCode, hashCode.mul(JExpr.lit(31)).plus(
				JOp.cond(value.eq(JExpr._null()), JExpr.lit((int) 0),
						valueHashCode)));
	}

	protected abstract JExpression valueHashCode(JType type, JExpression value);
}