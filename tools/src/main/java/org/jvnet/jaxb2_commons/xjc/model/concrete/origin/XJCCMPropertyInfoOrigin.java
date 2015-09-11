package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.hisrc.xml.xsom.SchemaComponentAware;
import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.PropertyOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPropertyInfoOrigin;

import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XSComponent;

public class XJCCMPropertyInfoOrigin extends
		CMPropertyInfoOrigin<NType, NClass, CPropertyInfo> implements
		PropertyOutlineGeneratorFactory, SchemaComponentAware {

	public XJCCMPropertyInfoOrigin(CPropertyInfo source) {
		super(source);
	}

	public MPropertyOutlineGenerator createGenerator(Outline outline) {
		return new CMPropertyOutlineGenerator(outline, getSource());
	}

	public XSComponent getSchemaComponent() {
		return getSource().getSchemaComponent();
	}

}
