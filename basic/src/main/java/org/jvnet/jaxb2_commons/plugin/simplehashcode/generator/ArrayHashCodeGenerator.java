package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Arrays;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ArrayHashCodeGenerator extends ValueBasedHashCodeCodeGenerator {

	public ArrayHashCodeGenerator(TypedHashCodeCodeGeneratorFactory factory,
			JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected boolean isCheckForNullRequired() {
		return false;
	}

	@Override
	protected JExpression generateHashCode(JType type, JVar value) {
		return getCodeModel().ref(Arrays.class).staticInvoke("hashCode")
				.arg(value);
	}

}
