package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;

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
	public void append(JBlock block, JType exposedType, Collection<JType> possibleTypes,
			boolean isAlwaysSet, JVar currentHashCode,
			JVar value, JExpression hasSetValue) {

		final JExpression valueHashCode = generateHashCode(block, exposedType,
				value);

		final JExpression conditionalValueHashCode;
		if (isAlwaysSet || !isCheckForNullRequired()) {
			conditionalValueHashCode = valueHashCode;
		} else {
			conditionalValueHashCode = JOp.cond(hasSetValue, valueHashCode,
					JExpr.lit((int) 0));
		}
		block.assignPlus(currentHashCode, conditionalValueHashCode);
	}

	protected boolean isCheckForNullRequired() {
		return true;
	}

	protected abstract JExpression generateHashCode(JBlock block, JType type,
			JVar value);

}