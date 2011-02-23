package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

public interface MElementOutlineGenerator {
	
	public MClassOutline generate(MPackageOutline parent, MModelInfo modelInfo,
			MElementInfo elementInfo);

}
