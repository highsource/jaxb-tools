package org.jvnet.jaxb.annox.util.tests;

import org.junit.jupiter.api.Assertions;
import org.jvnet.jaxb.annox.util.ClassUtils;
import org.jvnet.jaxb.annox.util.ReflectionUtils;

public class ReflectionUtilsTest {

	public static class Test {
		public Test() {
		}

		public Test(String x) {
		}

		public void one() {
		}

		public void two() {
		}

		public void two(String value) {
		}

		public void three() {
		}

		public void three(String value) {
		}

		public void three(String[] value) {
		}

		public void three(Object value) {
		}

		public void three(Object[] value) {
		}

		public void four(String x, int y, char[] z) {
		}
	}

    @org.junit.jupiter.api.Test
	public void testGetMethod() throws Exception {

		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "one",
				ClassUtils.forNames(null)));
		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames(null)));
		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("")));
		Assertions.assertEquals(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("")), ReflectionUtils.getMethod(Test.class,
				"two", ClassUtils.forNames("")));

		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("java.lang.String")));
		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "three",
				ClassUtils.forNames("java.lang.String[]")));
		Assertions.assertNotNull(ReflectionUtils.getMethod(Test.class, "four",
				ClassUtils.forNames("java.lang.String, int, char[]")));

	}

    @org.junit.jupiter.api.Test
	public void testGetConstructor() throws Exception {

		ReflectionUtils.getConstructor(Test.class, null);
		ReflectionUtils
				.getConstructor(Test.class, ClassUtils.EMPTY_CLASS_ARRAY);
		ReflectionUtils.getConstructor(Test.class, ClassUtils.forNames(null));
		ReflectionUtils.getConstructor(Test.class, ClassUtils.forNames(""));
		ReflectionUtils.getConstructor(Test.class, ClassUtils
				.forNames("java.lang.String"));

	}
}
