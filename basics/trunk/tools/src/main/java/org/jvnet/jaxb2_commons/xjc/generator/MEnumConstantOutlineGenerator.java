package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

public interface MEnumConstantOutlineGenerator {

	public MEnumConstantOutline generate(MEnumOutline parent,
			MModelInfo modelInfo, MEnumConstantInfo enumConstantInfo);

}
