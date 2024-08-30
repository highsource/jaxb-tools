package org.jvnet.hyperjaxb3.lang.builder.tests;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberEqualsTest {
    @Test
	public void testBigDecimal() throws Exception {

		final BigDecimal a = new BigDecimal("2.001");
		final BigDecimal b = new BigDecimal("2.0010");

		Assertions.assertTrue(a.compareTo(b) == 0);
	}

}
