package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.lang.JAXBEqualsStrategy;

public class GH22Test {
	@Test
	public void testJAXBEqualsSymmetryConcreteClassAndEnum() {
		Assertions.assertFalse(JAXBEqualsStrategy.getInstance().equals(null, null,
				new SomeConcreteClass(), SomeEnum.ENUM));
	}

	@Test
	public void testJAXBEqualsSymmetryEnumAndConcreteClass() {
		// This test fails and throws a ClassCastException
		Assertions.assertFalse(JAXBEqualsStrategy.getInstance().equals(null, null,
				SomeEnum.ENUM, new SomeConcreteClass()));
	}

	private static class SomeConcreteClass {
	}

	private enum SomeEnum {
		ENUM;
	}
}
