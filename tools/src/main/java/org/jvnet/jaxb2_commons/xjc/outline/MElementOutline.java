package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MElementOutline extends MChildOutline, MPackagedOutline,
		MTargeted<MElementInfo<NType, NClass>> {

	public JDefinedClass getCode();
}
