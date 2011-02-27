package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MEnumConstantOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMEnumConstantOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.EnumConstantOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumConstantInfoOrigin;

import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMEnumConstantInfoOrigin extends
		CMEnumConstantInfoOrigin<NType, NClass, CEnumConstant> implements
		EnumConstantOutlineGeneratorFactory {

	public XJCCMEnumConstantInfoOrigin(CEnumConstant source) {
		super(source);
	}

	@Override
	public MEnumConstantOutlineGenerator createGenerator(Outline outline) {
		return new CMEnumConstantOutlineGenerator(outline, getSource());
	}
}
