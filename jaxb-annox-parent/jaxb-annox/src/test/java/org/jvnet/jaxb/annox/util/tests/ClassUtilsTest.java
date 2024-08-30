package org.jvnet.jaxb.annox.util.tests;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.util.ClassUtils;

public class ClassUtilsTest {

	@Test
	public void testwrapperArrayToPrimitiveArray() {
		Assertions.assertEquals(int[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(Integer[].class));
		Assertions.assertEquals(int[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(int[].class));
		Assertions.assertEquals(Object[].class,
				ClassUtils.wrapperArrayToPrimitiveArray(Object[].class));
	}

	@Test
	public void testForName() throws Exception {

		Assertions.assertEquals(Integer.TYPE,
				ClassUtils.forName("int"), "Wrong class.");
		Assertions.assertEquals(byte[].class,
				ClassUtils.forName("byte[]"), "Wrong class.");
		Assertions.assertEquals(String.class,
				ClassUtils.forName("java.lang.String"), "Wrong class.");
		Assertions.assertEquals(URL[].class,
				ClassUtils.forName("java.net.URL[]"), "Wrong class.");

	}

	@Test
	public void testForNames() throws Exception {

		Assertions.assertTrue(
				Arrays.equals(
						new Class<?>[] { Integer.TYPE, String.class,
								int[].class, String[].class, Map.Entry.class },
						ClassUtils
								.forNames("int, java.lang.String, int[], java.lang.String[], java.util.Map$Entry")),
            "Class arrays must be equal.");
	}

}
