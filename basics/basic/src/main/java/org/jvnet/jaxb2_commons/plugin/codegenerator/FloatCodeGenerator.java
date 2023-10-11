package org.jvnet.jaxb2_commons.plugin.codegenerator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class FloatCodeGenerator<A extends Arguments<A>> extends
		AbstractCodeGenerator<A> {

	public FloatCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void generate(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onFloat(arguments, block, isAlwaysSet);
	}

}
