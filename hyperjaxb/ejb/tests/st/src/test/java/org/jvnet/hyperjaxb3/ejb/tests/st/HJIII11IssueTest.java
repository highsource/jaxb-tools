package org.jvnet.hyperjaxb3.ejb.tests.st;

import jakarta.persistence.Column;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HJIII11IssueTest {

    @Test
	public void testSimpleTypes() {
		final Class<?> theClass = Root123456789012345678901234567890Type.class;

		Assertions.assertEquals(400, getColumn(theClass, "getLargeMinLength").length());
		Assertions.assertEquals(4000, getColumn(theClass, "getLargeMaxLength").length());
		Assertions.assertEquals(255, getColumn(theClass, "getMinLength").length());
		Assertions.assertEquals(10, getColumn(theClass, "getMaxLength").length());
		Assertions.assertEquals(8, getColumn(theClass, "getLength").length());
		Assertions.assertEquals(5, getColumn(theClass, "getDigits").precision());
		Assertions.assertEquals(2, getColumn(theClass, "getDigits").scale());
		Assertions.assertEquals(20, getColumn(theClass, "getTotalDigits").precision());
		Assertions.assertEquals(10, getColumn(theClass, "getTotalDigits").scale());
		Assertions.assertEquals(20, getColumn(theClass, "getFractionDigits").precision());
		Assertions.assertEquals(2, getColumn(theClass, "getFractionDigits").scale());
	}

	private Column getColumn(final Class<?> theClass, final String name) {
		try {
			return theClass.getMethod(name, new Class[0]).getAnnotation(Column.class);
		} catch (NoSuchMethodException nsmex) {
            return Assertions.fail(nsmex.getMessage());
		}
	}

}
