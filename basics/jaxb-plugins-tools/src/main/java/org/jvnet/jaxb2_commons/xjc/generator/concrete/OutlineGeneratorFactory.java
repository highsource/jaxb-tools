package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import com.sun.tools.xjc.outline.Outline;

public interface OutlineGeneratorFactory<G> {

	public G createGenerator(Outline outline);

}
