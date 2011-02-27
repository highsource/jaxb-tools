package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MElementOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

public interface MElementOutlineGenerator {

	public MElementOutline generate(MPackageOutline parent,
			MModelInfo modelInfo, MElementInfo elementInfo);

}
