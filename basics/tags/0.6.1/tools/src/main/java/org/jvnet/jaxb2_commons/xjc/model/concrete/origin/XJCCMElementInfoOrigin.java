package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MElementOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMElementOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.ElementOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementInfoOrigin;

import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMElementInfoOrigin extends
		CMElementInfoOrigin<NType, NClass, CElementInfo> implements
		ElementOutlineGeneratorFactory {

	public XJCCMElementInfoOrigin(CElementInfo source) {
		super(source);
	}

	public MElementOutlineGenerator createGenerator(Outline outline) {
		return new CMElementOutlineGenerator(outline, getSource());
	}

}
