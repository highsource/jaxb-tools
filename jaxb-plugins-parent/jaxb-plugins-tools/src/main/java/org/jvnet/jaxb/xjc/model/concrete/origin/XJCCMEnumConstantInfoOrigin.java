package org.jvnet.jaxb.xjc.model.concrete.origin;

import org.hisrc.xml.xsom.SchemaComponentAware;
import org.jvnet.jaxb.xjc.generator.MEnumConstantOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.CMEnumConstantOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.EnumConstantOutlineGeneratorFactory;
import org.jvnet.jaxb.xml.bind.model.concrete.origin.CMEnumConstantInfoOrigin;

import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XSComponent;

public class XJCCMEnumConstantInfoOrigin extends
		CMEnumConstantInfoOrigin<NType, NClass, CEnumConstant> implements
		EnumConstantOutlineGeneratorFactory, SchemaComponentAware {

	public XJCCMEnumConstantInfoOrigin(CEnumConstant source) {
		super(source);
	}

	public MEnumConstantOutlineGenerator createGenerator(Outline outline) {
		return new CMEnumConstantOutlineGenerator(outline, getSource());
	}

	@Override
	public XSComponent getSchemaComponent() {
		return getSource().getSchemaComponent();
	}
}
