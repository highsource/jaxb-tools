package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

public interface ToString2 {

	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy2 toStringStrategy);

	public StringBuilder appendFields(ObjectLocator locator, StringBuilder stringBuilder,
			ToStringStrategy2 toStringStrategy);

}
