package org.jvnet.jaxb.xjc.generator;

import org.jvnet.jaxb.xjc.outline.MEnumOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MEnumOutlineGenerator {

	public MEnumOutline generate(MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MEnumLeafInfo<NType, NClass> enumLeafInfo);
}
