package org.jvnet.jaxb2_commons.plugin.simplify.tests01;

import java.util.Date;

import javax.xml.datatype.Duration;

import org.junit.Test;

public class Gh2Test {

	@Test
	public void compiles() {
		final Gh2 item = new Gh2();
		@SuppressWarnings("unused")
		final String a = item.getA();
		@SuppressWarnings("unused")
		final Date b = item.getB();
		@SuppressWarnings("unused")
		final Duration c = item.getC();
	}
}
