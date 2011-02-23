package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MObjectFactoryOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

public interface MObjectFactoryOutlineGenerator {

	public MObjectFactoryOutline generate(MPackageOutline parent,
			MModelInfo modelInfo, MPackageInfo packageInfo);

}
