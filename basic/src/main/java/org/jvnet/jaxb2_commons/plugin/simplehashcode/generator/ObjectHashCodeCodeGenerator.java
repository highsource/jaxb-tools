package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ObjectHashCodeCodeGenerator extends
		ValueBasedHashCodeCodeGenerator {

	public ObjectHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected JExpression valueHashCode(JType type, JVar value) {
		// TODO multiple possible types
		// value.hashCode()
		return value.invoke("hashCode");
	}
}