package org.jvnet.jaxb2_commons.tests.issues;

import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class IssueJIIB42EqualsStrategy extends JAXBEqualsStrategy {

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		if (lhs instanceof IssueJIIB1Type && rhs instanceof IssueJIIB1Type) {
			// Quasi custom equals
			return super.equals(leftLocator, rightLocator, lhs, rhs);
		} else {
			return super.equals(leftLocator, rightLocator, lhs, rhs);
		}
	}
}
