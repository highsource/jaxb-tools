package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

@Deprecated
public interface HashCode {

	public int hashCode(ObjectLocator locator, HashCodeStrategy hashCodeStrategy);
}
