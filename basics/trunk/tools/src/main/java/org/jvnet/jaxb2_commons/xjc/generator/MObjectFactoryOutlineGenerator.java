package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MObjectFactoryOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MObjectFactoryOutlineGenerator {

	public MObjectFactoryOutline generate(MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo, MPackageInfo packageInfo);

}
