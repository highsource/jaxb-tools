package org.jvnet.jaxb.xjc.generator;

import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MClassOutlineGenerator {

	public MClassOutline generate(MPackageOutline parent, MModelInfo<NType, NClass> modelInfo,
			MClassInfo<NType, NClass> classInfo);

}
