package org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2;

import javax.persistence.OrderColumn;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.jaxb.annox.reflect.AnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.DualAnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

public class HJIII73Test extends TestCase {

	public void testLengthAnnotation() throws Exception {

		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();

		final ParameterizedAnnotatedElement o2m = aef
				.getAnnotatedElement(HJIII73Parent.class
						.getMethod("getHJIII73ChildOneToMany"));
		final ParameterizedAnnotatedElement m2m = aef
				.getAnnotatedElement(HJIII73Parent.class
						.getMethod("getHJIII73ChildManyToMany"));

		Assert.assertNotNull(o2m.getAnnotation(OrderColumn.class));
		Assert.assertTrue(o2m.getAnnotation(OrderColumn.class).name().length() > 0);
		Assert.assertNotNull(m2m.getAnnotation(OrderColumn.class));
		Assert.assertEquals("ORDNUNG", m2m.getAnnotation(OrderColumn.class)
				.name());
	}

}
