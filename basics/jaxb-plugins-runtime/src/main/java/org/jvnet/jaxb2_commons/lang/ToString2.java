package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface ToString2 {

	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy2 toStringStrategy);

	public StringBuilder appendFields(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy2 toStringStrategy);

}
