package org.jvnet.jaxb2_commons.plugin.simplify.tests01;

import org.junit.Test;

public class Gh2Test {

	@Test
	public void compiles()
	{
		final Gh2 item = new Gh2();
		item.getA();
		item.getB();
		item.getC();
		
	}
}
