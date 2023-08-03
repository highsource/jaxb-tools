package org.jvnet.jaxb2_commons.plugin.codegenerator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JCodeModel;

public abstract class AbstractCodeGenerationImplementor<A extends Arguments<A>>
		implements CodeGenerationImplementor<A> {

	private final JCodeModel codeModel;

	public AbstractCodeGenerationImplementor(JCodeModel codeModel) {
		this.codeModel = Validate.notNull(codeModel);
	}

	@Override
	public JCodeModel getCodeModel() {
		return codeModel;
	}

}
