package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ByteHashCodeCodeGenerator extends PrimitiveHashCodeCodeGenerator {
	public ByteHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public JExpression hashCodeValue(JType type, JVar value) {
		return JExpr.cast(getCodeModel().INT, value);
	}
}