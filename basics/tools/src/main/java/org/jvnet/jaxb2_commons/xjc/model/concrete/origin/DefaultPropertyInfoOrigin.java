package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import java.util.Objects;
import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.PropertyOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

import com.sun.tools.xjc.outline.Outline;

public class DefaultPropertyInfoOrigin implements MPropertyInfoOrigin,
		PropertyOutlineGeneratorFactory {

	private final MPropertyOutlineGenerator generator;

	public DefaultPropertyInfoOrigin(MPropertyOutlineGenerator generator) {
		Objects.requireNonNull(generator, "Property outline generator must not be null.");
		this.generator = generator;
	}

	public MPropertyOutlineGenerator createGenerator(Outline outline) {
		return generator;
	}
}
