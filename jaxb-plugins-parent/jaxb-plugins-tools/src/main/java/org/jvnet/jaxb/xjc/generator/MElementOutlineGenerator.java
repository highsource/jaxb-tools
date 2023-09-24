package org.jvnet.jaxb.xjc.generator;

import org.jvnet.jaxb.xjc.outline.MElementOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MElementInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MElementOutlineGenerator {

	public MElementOutline generate(MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MElementInfo<NType, NClass> elementInfo);

}
