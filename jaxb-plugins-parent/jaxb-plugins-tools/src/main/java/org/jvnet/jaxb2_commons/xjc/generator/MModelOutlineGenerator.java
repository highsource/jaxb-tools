package org.jvnet.jaxb2_commons.xjc.generator;

import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MModelOutlineGenerator {

	public MModelOutline generate(MModelInfo<NType, NClass> modelInfo);

}
