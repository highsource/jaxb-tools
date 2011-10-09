package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MEnumOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMEnumOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.EnumOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumLeafInfoOrigin;

import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMEnumLeafInfoOrigin extends
		CMEnumLeafInfoOrigin<NType, NClass, CEnumLeafInfo> implements
		EnumOutlineGeneratorFactory {

	public XJCCMEnumLeafInfoOrigin(CEnumLeafInfo source) {
		super(source);
	}

	public MEnumOutlineGenerator createGenerator(Outline outline) {
		return new CMEnumOutlineGenerator(outline, getSource());
	}
}
