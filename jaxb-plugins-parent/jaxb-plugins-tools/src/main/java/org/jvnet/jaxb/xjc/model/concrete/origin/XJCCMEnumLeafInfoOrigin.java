package org.jvnet.jaxb.xjc.model.concrete.origin;

import org.hisrc.xml.xsom.SchemaComponentAware;
import org.jvnet.jaxb.xjc.generator.MEnumOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.CMEnumOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.EnumOutlineGeneratorFactory;
import org.jvnet.jaxb.xml.bind.model.concrete.origin.CMEnumLeafInfoOrigin;

import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XSComponent;

public class XJCCMEnumLeafInfoOrigin extends
		CMEnumLeafInfoOrigin<NType, NClass, CEnumLeafInfo> implements
		EnumOutlineGeneratorFactory, SchemaComponentAware {

	public XJCCMEnumLeafInfoOrigin(CEnumLeafInfo source) {
		super(source);
	}

	public MEnumOutlineGenerator createGenerator(Outline outline) {
		return new CMEnumOutlineGenerator(outline, getSource());
	}

	@Override
	public XSComponent getSchemaComponent() {
		return getSource().getSchemaComponent();
	}
}
