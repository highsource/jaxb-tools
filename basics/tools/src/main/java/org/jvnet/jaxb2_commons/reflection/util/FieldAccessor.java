package org.jvnet.jaxb2_commons.reflection.util;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Objects;

public class FieldAccessor<T> implements Accessor<T> {

	private final Field field;

	public FieldAccessor(Class<?> theClass, String fieldName, Class<T> type) {
		try {
			this.field = theClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException nsfex) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Could not retrieve the field [{0}] from the class [{1}].",
					fieldName, theClass), nsfex);

		} catch (SecurityException sex) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Could not retrieve the field [{0}] from the class [{1}].",
					fieldName, theClass), sex);
		}

		if (!type.equals(this.field.getType())) {
			throw new IllegalArgumentException(
					MessageFormat
							.format("The fieldfield [{0}] does not have the expected type [{1}].",
									this.field, type));
		}

		try {
			field.setAccessible(true);
		} catch (SecurityException sex) {
			throw new IllegalArgumentException(
					MessageFormat.format(
							"Could not make the field [{0}] of the class [{1}] accessible.",
							this.field, theClass), sex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Object target) {
		Objects.requireNonNull(target, "Target object must not be null.");
		try {
			return (T) field.get(target);
		} catch (IllegalAccessException iaex) {
			throw new IllegalArgumentException(iaex);
		}
	}

	@Override
	public void set(Object target, T value) {
		Objects.requireNonNull(target, "Target object must not be null.");
		try {
			this.field.set(target, value);
		} catch (IllegalAccessException iaex) {
			throw new IllegalArgumentException(iaex);
		}
	}

}
