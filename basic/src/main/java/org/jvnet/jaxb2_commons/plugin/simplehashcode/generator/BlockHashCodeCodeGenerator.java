package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class BlockHashCodeCodeGenerator extends
		AbstractHashCodeCodeGenerator {

	public BlockHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	public void generate(JBlock block, JVar currentHashCode, JType exposedType,
			Collection<JType> possibleTypes, JVar value,
			JExpression hasSetValue, boolean isAlwaysSet) {
		final JBlock valueBlock;

		if (isAlwaysSet) {
			valueBlock = block;
		} else {
			final JConditional ifHasSetValue = block._if(hasSetValue);
			valueBlock = ifHasSetValue._then();
			ifHasSetValue._else()
					.assign(currentHashCode,
							currentHashCode.mul(JExpr.lit(getFactory()
									.getMultiplier())));
		}
		generate(valueBlock, currentHashCode, exposedType, possibleTypes, value);
	}

	protected abstract void generate(JBlock block, JVar currentHashCode,
			JType exposedType, Collection<JType> possibleTypes, JVar value);

}