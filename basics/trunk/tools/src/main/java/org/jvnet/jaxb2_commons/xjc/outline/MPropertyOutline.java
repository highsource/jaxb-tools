package org.jvnet.jaxb2_commons.xjc.outline;

import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JExpression;

public interface MPropertyOutline extends MTargeted<MPropertyInfo> {

	public MPropertyInfo getTarget();

	public MClassOutline getClassOutline();

	public MPropertyAccessor createAccessor(JExpression target);

}
