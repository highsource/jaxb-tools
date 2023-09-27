package org.jvnet.jaxb.tests.issues;

import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;

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
