package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

public interface Equals2 {

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object that, EqualsStrategy2 equalsStrategy);
}
