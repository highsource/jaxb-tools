package org.jvnet.jaxb2_commons.plugin.codegenerator;

import com.sun.codemodel.JCodeModel;

import java.util.Objects;

public abstract class AbstractCodeGenerationImplementor<A extends Arguments<A>>
		implements CodeGenerationImplementor<A> {

	private final JCodeModel codeModel;

	public AbstractCodeGenerationImplementor(JCodeModel codeModel) {
		this.codeModel = Objects.requireNonNull(codeModel, "codeModel must not be null");
	}

	@Override
	public JCodeModel getCodeModel() {
		return codeModel;
	}

}
