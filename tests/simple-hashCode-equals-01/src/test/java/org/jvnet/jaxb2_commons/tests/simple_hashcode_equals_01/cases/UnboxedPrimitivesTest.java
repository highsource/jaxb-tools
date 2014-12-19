package org.jvnet.jaxb2_commons.tests.simple_hashcode_equals_01.cases;

import org.junit.Assert;
import org.junit.Test;

public class UnboxedPrimitivesTest {

	@Test
	public void equalsPrimitives() {
		Assert.assertEquals(new UnboxedPrimitives(), new UnboxedPrimitives());
	}

}
