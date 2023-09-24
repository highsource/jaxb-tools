package org.jvnet.jaxb.xjc.outline;

import org.jvnet.jaxb.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MTargeted;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MPropertyOutline extends
		MTargeted<MPropertyInfo<NType, NClass>>, MPropertyAccessorFactory {

	public MClassOutline getClassOutline();

}
