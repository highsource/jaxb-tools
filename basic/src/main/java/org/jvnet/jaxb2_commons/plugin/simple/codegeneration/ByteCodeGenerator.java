package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public class ByteCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public ByteCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		getImplementor().onByte(arguments, block, isAlwaysSet);
	}

}