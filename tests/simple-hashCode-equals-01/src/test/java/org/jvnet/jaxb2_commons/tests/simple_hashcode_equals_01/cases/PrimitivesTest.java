package org.jvnet.jaxb2_commons.tests.simple_hashcode_equals_01.cases;

import org.junit.Assert;
import org.junit.Test;

public class PrimitivesTest {

	@Test
	public void equalsPrimitives() {
		Assert.assertEquals(new Primitives(), new Primitives());
	}

}
