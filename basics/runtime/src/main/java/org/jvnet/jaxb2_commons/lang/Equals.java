package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@Deprecated
public interface Equals {

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object that, EqualsStrategy equalsStrategy);
}
