package org.jvnet.jaxb.annox.util.tests;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.annox.util.ClassUtils;

public class ClassUtilsTest {

	@Test
	public void testwrapperArrayToPrimitiveArray() {
		Assert.assertEquals(int[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(Integer[].class));
		Assert.assertEquals(int[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(int[].class));
		Assert.assertEquals(Object[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(Object[].class));
	}

	@Test
	public void testForName() throws Exception {

		Assert.assertEquals("Wrong class.", Integer.TYPE,
				ClassUtils.forName("int"));
		Assert.assertEquals("Wrong class.", byte[].class,
				ClassUtils.forName("byte[]"));
		Assert.assertEquals("Wrong class.", String.class,
				ClassUtils.forName("java.lang.String"));
		Assert.assertEquals("Wrong class.", URL[].class,
				ClassUtils.forName("java.net.URL[]"));

	}

	@Test
	public void testForNames() throws Exception {

		Assert.assertTrue(
				"Class arrays must be equal.",
				Arrays.equals(

						new Class<?>[] { Integer.TYPE, String.class,
								int[].class, String[].class, Map.Entry.class },
						ClassUtils
								.forNames("int, java.lang.String, int[], java.lang.String[], java.util.Map$Entry")));

	}

}
