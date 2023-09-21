package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

@Deprecated
public interface ToString {

	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy toStringStrategy);

	public StringBuilder appendFields(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy toStringStrategy);

}
