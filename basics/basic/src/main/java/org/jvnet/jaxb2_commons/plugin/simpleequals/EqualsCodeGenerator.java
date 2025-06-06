package org.jvnet.jaxb2_commons.plugin.simpleequals;

import org.jvnet.jaxb2_commons.plugin.codegenerator.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

import java.util.Objects;

public class EqualsCodeGenerator extends
		CodeGenerationAbstraction<EqualsArguments> {

	public EqualsCodeGenerator(JCodeModel codeModel) {
		super(new EqualsCodeGenerationImplementor(Objects.requireNonNull(codeModel, "codeModel must not be null")));
	}

}
