package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MEnumConstantOutlineGenerator {

	public MEnumConstantOutline generate(MEnumOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MEnumConstantInfo<NType, NClass> enumConstantInfo);

}
