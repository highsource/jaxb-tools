package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.CMPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.PropertyOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPropertyInfoOrigin;

import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMPropertyInfoOrigin extends
		CMPropertyInfoOrigin<NType, NClass, CPropertyInfo> implements
		PropertyOutlineGeneratorFactory {

	public XJCCMPropertyInfoOrigin(CPropertyInfo source) {
		super(source);
	}

	public MPropertyOutlineGenerator createGenerator(Outline outline) {
		return new CMPropertyOutlineGenerator(outline, getSource());
	}

}
