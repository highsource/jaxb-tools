package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

public interface MPropertyOutline extends MTargeted<MPropertyInfo>,
		MPropertyAccessorFactory {

	public MPropertyInfo getTarget();

	public MClassOutline getClassOutline();

}
