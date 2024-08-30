package org.jvnet.jaxb.plugin.simplify.tests01;

import org.junit.jupiter.api.Test;

import java.util.Date;

import javax.xml.datatype.Duration;

public class Gh2Test {

	@Test
	public void compiles() {
		final Gh2 item = new Gh2();
		@SuppressWarnings("unused")
		final String a = item.getA();
        item.isSetA();
		@SuppressWarnings("unused")
		final Date b = item.getB();
        item.isSetB();
		@SuppressWarnings("unused")
		final Duration c = item.getC();
        item.isSetC();
	}
}
