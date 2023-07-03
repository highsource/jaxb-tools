package org.jvnet.jaxb2_commons.plugin.simpleequals;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.plugin.codegenerator.CodeGenerationAbstraction;

import com.sun.codemodel.JCodeModel;

public class EqualsCodeGenerator extends
		CodeGenerationAbstraction<EqualsArguments> {

	public EqualsCodeGenerator(JCodeModel codeModel) {
		super(new EqualsCodeGenerationImplementor(Validate.notNull(codeModel)));
	}

}
