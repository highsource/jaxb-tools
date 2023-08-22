package org.jvnet.hyperjaxb3.lang.reflect.util;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public static Object getFieldValue(Object instance, String fieldName) {
		final Class<?> theClass = instance.getClass();
		final Object fieldValue = getFieldValue(instance, fieldName, theClass);
		return fieldValue;
	}

	private static Object getFieldValue(Object instance, String fieldName,
			final Class<?> theClass) {
		try {
			final Field field = theClass.getDeclaredField(fieldName);
			boolean oldAccessible = field.isAccessible();
			try {
				field.setAccessible(true);
				return field.get(instance);
			} catch (IllegalArgumentException ex) {
				return null;
			} catch (IllegalAccessException ex) {
				return null;
			} finally {
				field.setAccessible(oldAccessible);
			}
		} catch (NoSuchFieldException nsfex) {
			if (theClass.getSuperclass() == null) {
				return null;
			} else {
				final Object fieldValue = getFieldValue(instance, fieldName,
						theClass.getSuperclass());
				return fieldValue;
			}
		}
	}
}
