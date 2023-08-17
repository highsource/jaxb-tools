package org.jvnet.annox.util;

import org.apache.commons.lang3.Validate;

public class ArrayUtils {

	@SuppressWarnings("unchecked")
	public static <T> T asPrimitiveArray(Object array) {
		if (array == null) {
			return null;
		}
		final Class<?> arrayClass = array.getClass();
		Validate.isTrue(arrayClass.isArray(), "Argument must be an array.");
		final Class<?> componentType = arrayClass.getComponentType();
		if (componentType.isPrimitive()) {
			return (T) array;
		} else if (Boolean.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Boolean[]) array);
		} else if (Byte.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Byte[]) array);
		} else if (Character.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Character[]) array);
		} else if (Short.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Short[]) array);
		} else if (Integer.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Integer[]) array);
		} else if (Long.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Long[]) array);
		} else if (Double.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Double[]) array);
		} else if (Float.class.equals(componentType)) {
			return (T) org.apache.commons.lang3.ArrayUtils
					.toPrimitive((Float[]) array);
		} else {
			return (T) array;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] asObjectArray(Object array) {
		if (array == null) {
			return null;
		}
		final Class<?> arrayClass = array.getClass();
		Validate.isTrue(arrayClass.isArray(), "Argument must be an array.");
		final Class<?> componentType = arrayClass.getComponentType();
		if (!componentType.isPrimitive()) {
			return (T[]) array;
		}
		if (Boolean.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((boolean[]) array);
		} else if (Byte.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((byte[]) array);
		} else if (Character.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((char[]) array);
		} else if (Short.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((short[]) array);
		} else if (Integer.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((int[]) array);
		} else if (Long.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((long[]) array);
		} else if (Double.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((double[]) array);
		} else if (Float.TYPE.equals(componentType)) {
			return (T[]) org.apache.commons.lang3.ArrayUtils
					.toObject((float[]) array);
		} else {
			throw new AssertionError("Unexpected primitive type ["
					+ componentType + "].");
		}
	}

}
