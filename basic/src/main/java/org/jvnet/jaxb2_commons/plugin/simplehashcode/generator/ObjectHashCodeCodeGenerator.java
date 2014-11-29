package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;

public class ObjectHashCodeCodeGenerator extends BasicHashCodeCodeGenerator {

	public ObjectHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	protected JExpression valueHashCode(JType type, JExpression value) {
		// value.hashCode()
		return value.invoke("hashCode");
	}
}