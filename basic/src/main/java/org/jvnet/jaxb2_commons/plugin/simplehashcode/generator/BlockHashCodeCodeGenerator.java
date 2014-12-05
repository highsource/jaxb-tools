package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
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
	public void append(JBlock block, JType type, Collection<JType> possibleTypes,
			boolean isAlwaysSet, JVar currentHashCode,
			JVar value, JExpression hasSetValue) {
		final JBlock subBlock;
		if (isAlwaysSet) {
			subBlock = block;
		} else {
			final JConditional ifHasSetValue = block._if(hasSetValue);
			subBlock = ifHasSetValue._then();
		}
		append(subBlock, currentHashCode, type, possibleTypes, value);
	}

	protected abstract void append(JBlock block, JVar currentHashCode,
			JType exposedType, Collection<JType> possibleTypes, JVar value);

}