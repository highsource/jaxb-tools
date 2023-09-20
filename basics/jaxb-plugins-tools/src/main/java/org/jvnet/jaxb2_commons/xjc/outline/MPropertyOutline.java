package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MPropertyOutline extends
		MTargeted<MPropertyInfo<NType, NClass>>, MPropertyAccessorFactory {

	public MClassOutline getClassOutline();

}
