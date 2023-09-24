package org.jvnet.jaxb.plugin.codegenerator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class BooleanCodeGenerator<A extends Arguments<A>> extends
		AbstractCodeGenerator<A> {

	public BooleanCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void generate(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onBoolean(arguments, block, isAlwaysSet);
	}

}
