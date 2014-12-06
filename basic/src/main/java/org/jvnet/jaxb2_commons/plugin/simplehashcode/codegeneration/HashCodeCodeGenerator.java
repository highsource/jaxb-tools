package org.jvnet.jaxb2_commons.plugin.simplehashcode.codegeneration;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.plugin.simple.codegeneration.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

public class HashCodeCodeGenerator extends
		CodeGenerationAbstraction<HashCodeArguments> {

	public HashCodeCodeGenerator(JCodeModel codeModel, int multiplier) {
		super(
				new HashCodeCodeGenerationImplementor(
						Validate.notNull(codeModel), multiplier));
	}

}
