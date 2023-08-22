package org.jvnet.hyperjaxb3.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.annox.model.annotation.value.XBooleanAnnotationValue;
import org.jvnet.annox.model.annotation.value.XEnumAnnotationValue;
import org.jvnet.annox.model.annotation.value.XIntAnnotationValue;
import org.jvnet.annox.model.annotation.value.XStringAnnotationValue;
import org.jvnet.annox.model.annotation.value.XXAnnotationAnnotationValue;

public class AnnotationUtils {

	public static <A extends Annotation> XAnnotationField<A> create(
			final String name, final XAnnotation<A> value) {
		if (value == null) {
			return null;
		} else {
			return new XSingleAnnotationField<A>(name,
					value.getAnnotationClass(),
					new XXAnnotationAnnotationValue<A>(value));
		}
	}

	public static XAnnotationField<String> create(final String name,
			final String value) {
		if (value == null) {
			return null;
		} else {
			return new XSingleAnnotationField<String>(name, String.class,
					new XStringAnnotationValue(value));
		}
	}

	public static XAnnotationField<Boolean> create(final String name,
			final Boolean value) {
		if (value == null) {
			return null;
		} else {
			return new XSingleAnnotationField<Boolean>(name, Boolean.class,
					new XBooleanAnnotationValue(value));
		}
	}

	public static XAnnotationField<Integer> create(final String name,
			final Integer value) {
		if (value == null) {
			return null;
		} else {
			return new XSingleAnnotationField<Integer>(name, Integer.class,
					new XIntAnnotationValue(value));
		}
	}

	public static <E extends Enum<E>> XAnnotationField<E> create(
			final String name, final E value) {
		if (value == null) {
			return null;
		} else {
			return new XSingleAnnotationField<E>(name, value.getClass(),
					new XEnumAnnotationValue<E>(value));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Annotation> XAnnotationField<Annotation[]> create(
			final String name, final XAnnotation<?>[] value,
			Class<T> annotationClass) {
		if (value == null) {
			return null;
		} else {
			final XXAnnotationAnnotationValue<Annotation>[] values = new XXAnnotationAnnotationValue[value.length];
			for (int index = 0; index < value.length; index++) {
				values[index] = new XXAnnotationAnnotationValue(value[index]);
			}

			return new XArrayAnnotationField<Annotation>(name, Array
					.newInstance(annotationClass, 0).getClass(), values);
		}
	}

	public static <E extends Enum<E>> XAnnotationField<E[]> create(
			final String name, final E[] value) {
		if (value == null) {
			return null;
		} else {

			@SuppressWarnings("unchecked")
			final XEnumAnnotationValue<E>[] values = new XEnumAnnotationValue[value.length];
			for (int index = 0; index < value.length; index++) {
				values[index] = new XEnumAnnotationValue<E>(value[index]);
			}
			return new XArrayAnnotationField<E>(name, value.getClass(), values);
		}
	}

	public static XAnnotationField<String[]> create(final String name,
			final String[] value) {
		if (value == null) {
			return null;
		} else {
			final XStringAnnotationValue[] values = new XStringAnnotationValue[value.length];
			for (int index = 0; index < value.length; index++) {
				values[index] = new XStringAnnotationValue(value[index]);
			}
			return new XArrayAnnotationField<String>(name, String[].class, values);
		}
	}

}
