package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

public interface MPackageOutlineGenerator {

	public MPackageOutline generate(MModelOutline parent,
			MModelInfo modelInfo, MPackageInfo packageInfo);

}
