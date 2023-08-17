package org.jvnet.annox.util.tests;

import org.junit.Assert;
import junit.framework.TestCase;

import org.jvnet.annox.util.ClassUtils;
import org.jvnet.annox.util.ReflectionUtils;

public class ReflectionUtilsTest extends TestCase {

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

	public void testGetMethod() throws Exception {

		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "one",
				ClassUtils.forNames(null)));
		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames(null)));
		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("")));
		Assert.assertEquals(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("")), ReflectionUtils.getMethod(Test.class,
				"two", ClassUtils.forNames("")));

		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "two",
				ClassUtils.forNames("java.lang.String")));
		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "three",
				ClassUtils.forNames("java.lang.String[]")));
		Assert.assertNotNull(ReflectionUtils.getMethod(Test.class, "four",
				ClassUtils.forNames("java.lang.String, int, char[]")));

	}

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
