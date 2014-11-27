package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JCodeModel;

public abstract class BaseEqualsCodeGenerator implements
		EqualsCodeGenerator {

	private final JCodeModel codeModel;

	public BaseEqualsCodeGenerator(JCodeModel codeModel) {
		this.codeModel = Validate.notNull(codeModel);
	}

	public JCodeModel getCodeModel() {
		return codeModel;
	}
}