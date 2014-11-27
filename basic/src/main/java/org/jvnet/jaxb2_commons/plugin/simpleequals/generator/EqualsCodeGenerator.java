package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import org.jvnet.jaxb2_commons.codemodel.generator.CodeGenerator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;

public interface EqualsCodeGenerator extends CodeGenerator {

	public void generate(JBlock block, JType type, JExpression left,
			JExpression right);
}
