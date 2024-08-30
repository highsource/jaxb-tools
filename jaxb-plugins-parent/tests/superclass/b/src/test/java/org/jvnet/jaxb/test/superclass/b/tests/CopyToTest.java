package org.jvnet.jaxb.test.superclass.b.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.test.superclass.b.AnotherObjectType;

public class CopyToTest {

	@Test
	public void correctlyCopies() {
		final AnotherObjectType source = new AnotherObjectType();
		source.setId("Id");
		source.setData("Data");
		final AnotherObjectType target = (AnotherObjectType) source.clone();
		Assertions.assertEquals("Id", target.getId());
		Assertions.assertEquals("Data", target.getData());
		Assertions.assertEquals(source, target);
		Assertions.assertEquals(source.hashCode(), target.hashCode());
	}
}
