package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import org.jvnet.jaxb2_commons.codemodel.generator.TypedCodeGeneratorFactory;

public interface TypedHashCodeCodeGeneratorFactory extends
		TypedCodeGeneratorFactory<HashCodeCodeGenerator> {

	public int getInitial();

	public int getMultiplier();
}
