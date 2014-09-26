package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface HashCode {

	public int hashCode(ObjectLocator locator, HashCodeStrategy hashCodeStrategy);
}
