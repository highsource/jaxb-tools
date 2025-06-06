package org.jvnet.jaxb.plugin.simpleequals;

import org.jvnet.jaxb.plugin.codegenerator.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

import java.util.Objects;

public class EqualsCodeGenerator extends
		CodeGenerationAbstraction<EqualsArguments> {

	public EqualsCodeGenerator(JCodeModel codeModel) {
		super(new EqualsCodeGenerationImplementor(Objects.requireNonNull(codeModel, "codeModel must not be null")));
	}

}
