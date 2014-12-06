package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class LongCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public LongCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onLong(arguments, block, isAlwaysSet);
	}

}