package org.jvnet.hyperjaxb3.ejb.tests.st;

import javax.persistence.Column;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class HJIII11IssueTest extends TestCase {

	public void testSimpleTypes() {
		final Class<?> theClass = Root123456789012345678901234567890Type.class;

		assertEquals(400, getColumn(theClass, "getLargeMinLength").length());
		assertEquals(4000, getColumn(theClass, "getLargeMaxLength").length());
		assertEquals(255, getColumn(theClass, "getMinLength").length());
		assertEquals(10, getColumn(theClass, "getMaxLength").length());
		assertEquals(8, getColumn(theClass, "getLength").length());
		assertEquals(5, getColumn(theClass, "getDigits").precision());
		assertEquals(2, getColumn(theClass, "getDigits").scale());
		assertEquals(20, getColumn(theClass, "getTotalDigits").precision());
		assertEquals(10, getColumn(theClass, "getTotalDigits").scale());
		assertEquals(20, getColumn(theClass, "getFractionDigits").precision());
		assertEquals(2, getColumn(theClass, "getFractionDigits").scale());
	}

	private Column getColumn(final Class<?> theClass, final String name) {
		try {
			return theClass.getMethod(name, new Class[0]).getAnnotation(
					Column.class);
		} catch (NoSuchMethodException nsmex) {
			throw new AssertionFailedError(nsmex.getMessage());
		}
	}

}
