package org.jvnet.jaxb.xjc.outline;

import java.util.List;

import org.jvnet.jaxb.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb.xml.bind.model.MTargeted;

import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MEnumOutline extends MChildOutline, MPackagedOutline,
		MTargeted<MEnumLeafInfo<NType, NClass>> {

	public List<MEnumConstantOutline> getEnumConstantOutlines();

	public JDefinedClass getCode();
}
