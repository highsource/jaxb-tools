package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import org.jvnet.jaxb2_commons.codemodel.generator.CodeGenerator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public interface HashCodeCodeGenerator extends CodeGenerator {

	public void generate(JBlock block, JType type, JVar hashCode,
			JVar value);
}
