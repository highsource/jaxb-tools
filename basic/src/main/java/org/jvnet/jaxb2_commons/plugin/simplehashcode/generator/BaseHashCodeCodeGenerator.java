package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JCodeModel;

public abstract class BaseHashCodeCodeGenerator implements
		HashCodeCodeGenerator {

	private final JCodeModel codeModel;

	public BaseHashCodeCodeGenerator(JCodeModel codeModel) {
		this.codeModel = Validate.notNull(codeModel);
	}

	public JCodeModel getCodeModel() {
		return codeModel;
	}
}