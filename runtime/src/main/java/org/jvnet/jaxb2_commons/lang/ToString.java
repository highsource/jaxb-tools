package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface ToString {

	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy toStringStrategy);

	public StringBuilder appendFields(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy toStringStrategy);

}
