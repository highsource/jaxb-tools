package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class IntCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public IntCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onInt(arguments, block, isAlwaysSet);
	}

}