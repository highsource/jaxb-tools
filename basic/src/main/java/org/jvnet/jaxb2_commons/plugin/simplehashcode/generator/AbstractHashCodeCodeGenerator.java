package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JCodeModel;

public abstract class AbstractHashCodeCodeGenerator implements
		HashCodeCodeGenerator {

	private final TypedHashCodeCodeGeneratorFactory factory;
	private final JCodeModel codeModel;

	public AbstractHashCodeCodeGenerator(TypedHashCodeCodeGeneratorFactory factory,
			JCodeModel codeModel) {
		this.factory = Validate.notNull(factory);
		this.codeModel = Validate.notNull(codeModel);
	}

	public TypedHashCodeCodeGeneratorFactory getFactory() {
		return factory;
	}

	public JCodeModel getCodeModel() {
		return codeModel;
	}
}