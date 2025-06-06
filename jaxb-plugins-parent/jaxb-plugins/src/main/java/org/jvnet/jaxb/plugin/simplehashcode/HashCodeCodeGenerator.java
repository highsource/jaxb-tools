package org.jvnet.jaxb.plugin.simplehashcode;

import org.jvnet.jaxb.plugin.codegenerator.CodeGenerationAbstraction;

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
