package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

@Deprecated
public interface Equals {

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object that, EqualsStrategy equalsStrategy);
}
