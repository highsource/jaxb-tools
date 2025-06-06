package org.jvnet.jaxb2_commons.plugin.codegenerator;

import com.sun.codemodel.JCodeModel;

import java.util.Objects;

public abstract class AbstractCodeGenerator<A extends Arguments<A>> implements
		CodeGenerator<A> {

	private final CodeGenerator<A> codeGenerator;
	private final CodeGenerationImplementor<A> implementor;
	private final JCodeModel codeModel;

	public AbstractCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		this.codeGenerator = Objects.requireNonNull(codeGenerator, "codeGenerator must not be null");
		this.implementor = Objects.requireNonNull(implementor, "implementor must not be null");
		this.codeModel = implementor.getCodeModel();
	}

	public CodeGenerationImplementor<A> getImplementor() {
		return implementor;
	}

	public CodeGenerator<A> getCodeGenerator() {
		return codeGenerator;
	}

	public JCodeModel getCodeModel() {
		return codeModel;
	}
}
