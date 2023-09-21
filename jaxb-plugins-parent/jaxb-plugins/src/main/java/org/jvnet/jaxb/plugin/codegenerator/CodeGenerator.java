package org.jvnet.jaxb.plugin.codegenerator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public interface CodeGenerator<A extends Arguments<A>> {

	public void generate(JBlock block, JType type, Collection<JType> possibleTypes,
			boolean isAlwaysSet, A arguments);
}
