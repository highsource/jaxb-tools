package org.jvnet.hyperjaxb3.lang.builder.tests;

import java.math.BigDecimal;

import junit.framework.TestCase;

public class NumberEqualsTest extends TestCase {
	
	public void testBigDecimal() throws Exception {
		
		final BigDecimal a = new BigDecimal("2.001");
		final BigDecimal b = new BigDecimal("2.0010");
		
		assertTrue(a.compareTo(b) == 0);
	}

}
