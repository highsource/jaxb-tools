package org.jvnet.jaxb2_commons.codemodel.generator;

import com.sun.codemodel.JType;

public interface TypedCodeGeneratorFactory<CG extends CodeGenerator> {

	CG getCodeGenerator(JType type);
}
