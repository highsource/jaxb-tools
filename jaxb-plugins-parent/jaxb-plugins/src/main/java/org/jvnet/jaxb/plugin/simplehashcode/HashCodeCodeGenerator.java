package org.jvnet.jaxb.plugin.simplehashcode;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.plugin.codegenerator.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

public class HashCodeCodeGenerator extends
		CodeGenerationAbstraction<HashCodeArguments> {

	public HashCodeCodeGenerator(JCodeModel codeModel) {
		super(
				new HashCodeCodeGenerationImplementor(
						Validate.notNull(codeModel)));
	}

}
