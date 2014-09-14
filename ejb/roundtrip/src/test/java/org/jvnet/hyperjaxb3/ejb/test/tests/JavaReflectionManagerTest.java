package org.jvnet.hyperjaxb3.ejb.test.tests;

import junit.framework.TestCase;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

public class JavaReflectionManagerTest extends TestCase {

	public void testXClass() throws Exception {

		final JavaReflectionManager manager = new JavaReflectionManager();
		final XClass cClass = manager.toXClass(Test.class);

		cClass.getDeclaredProperties(XClass.ACCESS_FIELD);
		cClass.getDeclaredProperties(XClass.ACCESS_PROPERTY);

	}

	public static class Test {

		private Generic<Object[]> e;

		public Generic<Object[]> getE() {
			return e;
		}

		public void setE(Generic<Object[]> e) {
			this.e = e;
		}

		private Generic<Object> f;

		public Generic<Object> getF() {
			return f;
		}

		public void setF(Generic<Object> f) {
			this.f = f;
		}

	}

	public static class Generic<T> {
	}

}
