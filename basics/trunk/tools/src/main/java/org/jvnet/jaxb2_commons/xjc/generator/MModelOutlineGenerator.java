package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

public interface MModelOutlineGenerator {

	public MModelOutline generate(MModelInfo modelInfo);

}
