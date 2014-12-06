package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class CharCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public CharCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onChar(arguments, block, isAlwaysSet);
	}

}