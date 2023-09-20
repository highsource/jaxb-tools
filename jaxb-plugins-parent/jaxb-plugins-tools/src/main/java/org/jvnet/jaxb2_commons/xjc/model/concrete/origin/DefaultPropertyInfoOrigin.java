package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.PropertyOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

import com.sun.tools.xjc.outline.Outline;

public class DefaultPropertyInfoOrigin implements MPropertyInfoOrigin,
		PropertyOutlineGeneratorFactory {

	private final MPropertyOutlineGenerator generator;

	public DefaultPropertyInfoOrigin(MPropertyOutlineGenerator generator) {
		Validate.notNull(generator);
		this.generator = generator;
	}

	public MPropertyOutlineGenerator createGenerator(Outline outline) {
		return generator;
	}
}
