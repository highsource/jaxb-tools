package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MClassOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMClassOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.ClassOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMClassInfoOrigin;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMClassInfoOrigin extends
		CMClassInfoOrigin<NType, NClass, CClassInfo> implements
		ClassOutlineGeneratorFactory {

	public XJCCMClassInfoOrigin(CClassInfo source) {
		super(source);
	}

	public MClassOutlineGenerator createGenerator(Outline outline) {
		return new CMClassOutlineGenerator(outline, getSource());
	}

}
