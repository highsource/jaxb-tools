package org.jvnet.jaxb.lang.tests;

import java.util.IdentityHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.jvnet.jaxb.lang.CopyStrategy;
import org.jvnet.jaxb.lang.CopyTo;
import org.jvnet.jaxb.lang.JAXBCopyStrategy;
import org.jvnet.jaxb.locator.DefaultRootObjectLocator;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.locator.util.LocatorUtils;

public class CyclicTests extends TestCase {

	public interface CopyToInstance extends CopyTo {
	}

	public static class A implements CopyToInstance {
		public B b;

		public Object createNewInstance() {
			return new A();
		}

		public Object copyTo(Object target) {
			return copyTo(null, target, JAXBCopyStrategy.getInstance());
		}

		public Object copyTo(ObjectLocator locator, Object target,
				CopyStrategy copyStrategy) {
			final A that = (A) target;
			that.b = (B) copyStrategy.copy(
					LocatorUtils.property(locator, "b", this.b), this.b,
					this.b != null);
			return that;
		}

	}

	public static class B implements CopyToInstance {
		public A a;

		public Object createNewInstance() {
			return new B();
		}

		public Object copyTo(Object target) {
			return copyTo(null, target, JAXBCopyStrategy.getInstance());
		}

		public Object copyTo(ObjectLocator locator, Object target,
				CopyStrategy copyStrategy) {
			final B that = (B) target;
			that.a = (A) copyStrategy.copy(
					LocatorUtils.property(locator, "a", this.a), this.a,
					this.a != null);
			return that;
		}
	}

	public void testCycle() throws Exception {
		final A a = new A();
		final B b = new B();
		a.b = b;
		b.a = a;

		final A a1 = (A) new JAXBCopyStrategy() {
			private Map<Object, Object> copies = new IdentityHashMap<Object, Object>();

			@Override
			public Object copy(ObjectLocator locator, Object object,
					boolean objectSet) {
				final Object existingCopy = copies.get(object);
				if (existingCopy != null) {
					return existingCopy;
				} else {
					if (object instanceof CopyToInstance) {
						final CopyToInstance source = (CopyToInstance) object;
						final Object newCopy = source.createNewInstance();
						copies.put(object, newCopy);
						source.copyTo(locator, newCopy, this);
						return newCopy;
					} else {
						final Object newCopy = super.copy(locator, object, objectSet);
						copies.put(object, newCopy);
						return newCopy;
					}
				}
			}
		}.copy(new DefaultRootObjectLocator(a), a);

		assertSame(a1.b.a.b, a1.b);
		// BUG: See https://github.com/highsource/jaxb-tools/issues/92
		// assertSame(a1.b.a, a1);
	}

}
