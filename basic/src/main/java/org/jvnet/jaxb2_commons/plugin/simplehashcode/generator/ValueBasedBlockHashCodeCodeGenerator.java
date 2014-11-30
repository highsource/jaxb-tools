package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Set;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class ValueBasedBlockHashCodeCodeGenerator extends
		AbstractHashCodeCodeGenerator {

	public ValueBasedBlockHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	public void generate(JBlock block, JVar currentHashCode, JType exposedType,
			Set<JType> possibleTypes, JVar value, JExpression hasSetValue, boolean isAlwaysSet) {
		final JExpression valueHashCode = valueHashCode(block, exposedType, value);

		final JExpression newHashCodeValue;
		if (isAlwaysSet) {
			newHashCodeValue = valueHashCode;
		} else {
			newHashCodeValue = JOp.cond(hasSetValue, valueHashCode,
					JExpr.lit((int) 0));
		}
		block.assign(currentHashCode,
				currentHashCode.mul(JExpr.lit(getFactory().getMultiplier()))
						.plus(newHashCodeValue));

	}

	protected abstract JExpression valueHashCode(JBlock block, JType type,
			JVar value);

}