package org.jvnet.jaxb.annox.util;

import java.lang.reflect.Array;

import org.apache.commons.lang3.Validate;

public class ObjectUtils {

	@SuppressWarnings("unchecked")
	public static Object valueOf(Class<?> basicType, String value)
			throws ClassNotFoundException, NumberFormatException,
			IllegalArgumentException {
		if (value == null) {
			return null;
		}
		final Class<?> type = ClassUtils.primitiveToWrapper(basicType);
		if (Boolean.class.equals(type)) {
			return Boolean.valueOf(value);
		} else if (Byte.class.equals(type)) {
			return Byte.valueOf(value);
		} else if (Character.class.equals(type)) {
			Validate.isTrue(value.length() == 1,
					"One character string expected.");
			return Character.valueOf(value.charAt(0));
		} else if (Short.class.equals(type)) {
			return Short.valueOf(value);
		} else if (Integer.class.equals(type)) {
			return Integer.valueOf(value);
		} else if (Long.class.equals(type)) {
			return Long.valueOf(value);
		} else if (Double.class.equals(type)) {
			return Double.valueOf(value);
		} else if (Float.class.equals(type)) {
			return Float.valueOf(value);
		} else if (Enum.class.isAssignableFrom(type)) {
			@SuppressWarnings("rawtypes")
			final Class<? extends Enum> enumClass = (Class<? extends Enum>) type;
			return Enum.valueOf(enumClass, value);
		} else if (Class.class.equals(type)) {
			return Class.forName(value);
		} else if (String.class.equals(type)) {
			return value;
		} else {
			throw new IllegalArgumentException("The type [" + basicType
					+ "] is not recognized.");
		}
	}

	public static Object[] valueOf(Class<?> basicType, String[] values)
			throws ClassNotFoundException, NumberFormatException,
			IllegalArgumentException {
		if (values == null) {
			return null;
		}
		final Class<?> type = ClassUtils.primitiveToWrapper(basicType);

		final Object[] array = (Object[]) Array
				.newInstance(type, values.length);

		for (int index = 0; index < values.length; index++) {
			final String value = values[index];
			array[index] = valueOf(type, value);
		}
		return array;
	}

}
