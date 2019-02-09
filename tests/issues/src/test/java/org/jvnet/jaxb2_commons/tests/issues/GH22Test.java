package org.jvnet.jaxb2_commons.tests.issues;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;

public class GH22Test {
	@Test
	public void testJAXBEqualsSymmetryConcreteClassAndEnum() {
		Assert.assertFalse(JAXBEqualsStrategy.getInstance().equals(null, null,
				new SomeConcreteClass(), SomeEnum.ENUM));
	}

	@Test
	public void testJAXBEqualsSymmetryEnumAndConcreteClass() {
		// This test fails and throws a ClassCastException
		Assert.assertFalse(JAXBEqualsStrategy.getInstance().equals(null, null,
				SomeEnum.ENUM, new SomeConcreteClass()));
	}

	private static class SomeConcreteClass {
	}

	private enum SomeEnum {
		ENUM;
	}
}