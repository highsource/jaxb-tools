package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MModelOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMModelOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.ModelOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMModelInfoOrigin;

import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMModelInfoOrigin extends
		CMModelInfoOrigin<NType, NClass, Model> implements
		ModelOutlineGeneratorFactory {

	public XJCCMModelInfoOrigin(Model source) {
		super(source);
	}

	@Override
	public MModelOutlineGenerator createGenerator(Outline outline) {
		return new CMModelOutlineGenerator(outline, getSource());
	}

}
