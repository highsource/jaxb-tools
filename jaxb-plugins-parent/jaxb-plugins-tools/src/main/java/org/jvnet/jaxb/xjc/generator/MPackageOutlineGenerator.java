package org.jvnet.jaxb.xjc.generator;

import org.jvnet.jaxb.xjc.outline.MModelOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;
import org.jvnet.jaxb.xml.bind.model.MPackageInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MPackageOutlineGenerator {

	public MPackageOutline generate(MModelOutline parent,
			MModelInfo<NType, NClass> modelInfo, MPackageInfo packageInfo);

}
