package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MClassOutlineGenerator {

	public MClassOutline generate(MPackageOutline parent, MModelInfo<NType, NClass> modelInfo,
			MClassInfo<NType, NClass> classInfo);

}
