package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@SuppressWarnings("deprecation")
public class DefaultCopyStrategy implements CopyStrategy2, CopyStrategy {

	protected Object copyInternal(ObjectLocator locator, Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof String) {
			return object;
		} else if (object instanceof Number) {
			return object;
		} else if (object instanceof CopyTo2) {
			return ((CopyTo2) object).copyTo(locator,
					((CopyTo2) object).createNewInstance(), this);
		} else if (object instanceof CopyTo) {
			return ((CopyTo) object).copyTo(locator,
					((CopyTo) object).createNewInstance(), this);
		} else if (object instanceof Cloneable) {
			return copyInternal(locator, (Cloneable) object);
		} else {
			return object;
		}
	}

	public Object copy(ObjectLocator locator, Object value) {
		if (value == null) {
			return null;
		}
		Class<?> lhsClass = value.getClass();
		if (!lhsClass.isArray()) {
			return copyInternal(locator, value);
		}
		// 'Switch' on type of array, to dispatch to the correct handler
		// This handles multi dimensional arrays of the same depth
		else if (value instanceof long[]) {
			return copy(locator, (long[]) value);
		} else if (value instanceof int[]) {
			return copy(locator, (int[]) value);
		} else if (value instanceof short[]) {
			return copy(locator, (short[]) value);
		} else if (value instanceof char[]) {
			return copy(locator, (char[]) value);
		} else if (value instanceof byte[]) {
			return copy(locator, (byte[]) value);
		} else if (value instanceof double[]) {
			return copy(locator, (double[]) value);
		} else if (value instanceof float[]) {
			return copy(locator, (float[]) value);
		} else if (value instanceof boolean[]) {
			return copy(locator, (boolean[]) value);
		} else {
			// Not an array of primitives
			return copy(locator, (Object[]) value);
		}
	}

	public long copy(ObjectLocator locator, long value) {
		return value;
	}

	public int copy(ObjectLocator locator, int value) {
		return value;
	}

	public short copy(ObjectLocator locator, short value) {
		return value;
	}

	public char copy(ObjectLocator locator, char value) {
		return value;
	}

	public byte copy(ObjectLocator locator, byte value) {
		return value;
	}

	public double copy(ObjectLocator locator, double value) {
		return value;
	}

	public float copy(ObjectLocator locator, float value) {
		return value;
	}

	public boolean copy(ObjectLocator locator, boolean value) {
		return value;
	}

	public Object[] copy(ObjectLocator locator, Object[] array) {
		if (array == null) {
			return null;
		}
		final Object[] copy = new Object[array.length];
		for (int index = 0; index < array.length; index++) {
			final Object element = array[index];
			final Object elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public long[] copy(ObjectLocator locator, long[] array) {
		if (array == null) {
			return null;
		}
		final long[] copy = new long[array.length];
		for (int index = 0; index < array.length; index++) {
			final long element = array[index];
			final long elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public int[] copy(ObjectLocator locator, int[] array) {
		if (array == null) {
			return null;
		}
		final int[] copy = new int[array.length];
		for (int index = 0; index < array.length; index++) {
			final int element = array[index];
			final int elementCopy = copy(item(locator, index, element), element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public short[] copy(ObjectLocator locator, short[] array) {
		if (array == null) {
			return null;
		}
		final short[] copy = new short[array.length];
		for (int index = 0; index < array.length; index++) {
			final short element = array[index];
			final short elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public char[] copy(ObjectLocator locator, char[] array) {
		if (array == null) {
			return null;
		}
		final char[] copy = new char[array.length];
		for (int index = 0; index < array.length; index++) {
			final char element = array[index];
			final char elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public byte[] copy(ObjectLocator locator, byte[] array) {
		if (array == null) {
			return null;
		}
		final byte[] copy = new byte[array.length];
		for (int index = 0; index < array.length; index++) {
			final byte element = array[index];
			final byte elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public double[] copy(ObjectLocator locator, double[] array) {
		if (array == null) {
			return null;
		}
		final double[] copy = new double[array.length];
		for (int index = 0; index < array.length; index++) {
			final double element = array[index];
			final double elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public float[] copy(ObjectLocator locator, float[] array) {
		if (array == null) {
			return null;
		}
		final float[] copy = new float[array.length];
		for (int index = 0; index < array.length; index++) {
			final float element = array[index];
			final float elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	public boolean[] copy(ObjectLocator locator, boolean[] array) {
		if (array == null) {
			return null;
		}
		final boolean[] copy = new boolean[array.length];
		for (int index = 0; index < array.length; index++) {
			final boolean element = array[index];
			final boolean elementCopy = copy(item(locator, index, element),
					element);
			copy[index] = elementCopy;
		}
		return copy;
	}

	protected Object copyInternal(ObjectLocator locator, Cloneable object) {
		return copyInternal(locator, object, false);
	}

	protected Object copyInternal(ObjectLocator locator, Cloneable object, boolean checkCloneable) {
		Class<?> clazz = object.getClass();
		Method method = null;

		if (checkCloneable) {
			Class<?> parentClazz = clazz.getSuperclass();

			while (parentClazz != null && hasCloneableInterface(parentClazz)) {
				clazz = parentClazz;
				parentClazz = clazz.getSuperclass();
			}
		}

		try {
			method = clazz.getMethod("clone", (Class[]) null);
		} catch (NoSuchMethodException nsmex) {
			method = null;
		}

		if (method == null || !Modifier.isPublic(method.getModifiers())) {

			throw new UnsupportedOperationException(
					"Could not clone object [" + object + "].",
					new CloneNotSupportedException(
							"Object class ["
									+ object.getClass()
									+ "] implements java.lang.Cloneable interface, "
									+ (checkCloneable ? ("with final determined class [" + clazz + "]") : "")
									+ "but does not provide a public no-arg clone() method. "
									+ "By convention, classes that implement java.lang.Cloneable "
									+ "should override java.lang.Object.clone() method (which is protected) "
									+ "with a public method."));
		}

		final boolean wasAccessible = method.isAccessible();
		try {
			if (!wasAccessible) {
				try {
					method.setAccessible(true);
				} catch (SecurityException ignore) {
				}
			}

			return method.invoke(object, (Object[]) null);
		} catch (Exception ex) {
			if (!checkCloneable && "java.lang.reflect.InaccessibleObjectException".equals(ex.getClass().getName())) {
				return copyInternal(locator, object, true);
			}
			throw new UnsupportedOperationException(
					"Could not clone the object ["
							+ object
							+ "] as invocation of the clone() method has thrown an exception.",
					ex);
		} finally {
			if (!wasAccessible) {
				try {
					method.setAccessible(false);
				} catch (SecurityException ignore) {
				}
			}
		}
	}

	private static boolean hasCloneableInterface(Class<?> clazz) {
		if (clazz != null) {
			for (Class<?> iface : clazz.getInterfaces()) {
				if (iface.isAssignableFrom(Cloneable.class)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean shouldBeCopiedAndSet(ObjectLocator locator, boolean valueSet) {
		return valueSet;
	}

	@Override
	public boolean copy(ObjectLocator locator, boolean value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public byte copy(ObjectLocator locator, byte value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public char copy(ObjectLocator locator, char value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public double copy(ObjectLocator locator, double value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public float copy(ObjectLocator locator, float value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public int copy(ObjectLocator locator, int value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public long copy(ObjectLocator locator, long value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public short copy(ObjectLocator locator, short value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public Object copy(ObjectLocator locator, Object value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public boolean[] copy(ObjectLocator locator, boolean[] value,
			boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public byte[] copy(ObjectLocator locator, byte[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public char[] copy(ObjectLocator locator, char[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public double[] copy(ObjectLocator locator, double[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public float[] copy(ObjectLocator locator, float[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public int[] copy(ObjectLocator locator, int[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public long[] copy(ObjectLocator locator, long[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public short[] copy(ObjectLocator locator, short[] value, boolean valueSet) {
		return copy(locator, value);
	}

	@Override
	public Object[] copy(ObjectLocator locator, Object[] value, boolean valueSet) {
		return copy(locator, value);
	}

	public static final DefaultCopyStrategy INSTANCE2 = new DefaultCopyStrategy();
	public static final DefaultCopyStrategy INSTANCE = INSTANCE2;

	public static DefaultCopyStrategy getInstance() {
		return INSTANCE2;
	}
}
