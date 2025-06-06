package org.jvnet.jaxb2_commons.plugin.simplehashcode;

import org.jvnet.jaxb2_commons.plugin.codegenerator.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

import java.util.Objects;

public class HashCodeCodeGenerator extends
		CodeGenerationAbstraction<HashCodeArguments> {

	public HashCodeCodeGenerator(JCodeModel codeModel) {
		super(
				new HashCodeCodeGenerationImplementor(
						Objects.requireNonNull(codeModel, "codeModel must not be null")));
	}

}
