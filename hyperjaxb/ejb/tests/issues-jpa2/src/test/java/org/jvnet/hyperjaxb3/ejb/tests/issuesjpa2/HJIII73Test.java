package org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2;

import jakarta.persistence.OrderColumn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.reflect.AnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.DualAnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

public class HJIII73Test {

    @Test
	public void testLengthAnnotation() throws Exception {

		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();

		final ParameterizedAnnotatedElement o2m = aef
				.getAnnotatedElement(HJIII73Parent.class
						.getMethod("getHJIII73ChildOneToMany"));
		final ParameterizedAnnotatedElement m2m = aef
				.getAnnotatedElement(HJIII73Parent.class
						.getMethod("getHJIII73ChildManyToMany"));

		Assertions.assertNotNull(o2m.getAnnotation(OrderColumn.class));
		Assertions.assertTrue(o2m.getAnnotation(OrderColumn.class).name().length() > 0);
		Assertions.assertNotNull(m2m.getAnnotation(OrderColumn.class));
		Assertions.assertEquals("ORDNUNG", m2m.getAnnotation(OrderColumn.class).name());
	}

}
