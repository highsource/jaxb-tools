package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JType;

public interface CodeGenerator<A extends Arguments> {

	public void append(JBlock block, JType type, Collection<JType> possibleTypes,
			boolean isAlwaysSet, A arguments);
}
