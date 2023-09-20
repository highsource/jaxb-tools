package org.jvnet.jaxb2_commons.test.superclass.b.tests;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.test.superclass.b.AnotherObjectType;

public class CopyToTest {

	@Test
	public void correctlyCopies() {
		final AnotherObjectType source = new AnotherObjectType();
		source.setId("Id");
		source.setData("Data");
		final AnotherObjectType target = (AnotherObjectType) source.clone();
		Assert.assertEquals("Id", target.getId());
		Assert.assertEquals("Data", target.getData());
		Assert.assertEquals(source, target);
		Assert.assertEquals(source.hashCode(), target.hashCode());
	}
}
