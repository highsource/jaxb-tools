package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

public interface MEnumOutlineGenerator {

	public MEnumOutline generate(MPackageOutline parent, MModelInfo modelInfo,
			MEnumLeafInfo enumLeafInfo);
}
