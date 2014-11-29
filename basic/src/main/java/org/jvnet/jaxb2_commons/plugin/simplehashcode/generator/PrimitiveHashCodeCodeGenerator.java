package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class PrimitiveHashCodeCodeGenerator extends
		PrimitiveBlockHashCodeCodeGenerator {

	public PrimitiveHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	protected JExpression hashCodeValue(JBlock block, JType type, JVar value) {
		return hashCodeValue(type, value);
	}

	protected abstract JExpression hashCodeValue(JType type, JVar value);
}