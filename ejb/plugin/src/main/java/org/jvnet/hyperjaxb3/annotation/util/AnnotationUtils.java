package org.jvnet.hyperjaxb3.annotation.util;

import java.lang.annotation.Annotation;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.annox.model.annotation.value.XXAnnotationAnnotationValue;
import org.jvnet.annox.parser.XAnnotationFieldParser;
import org.jvnet.annox.parser.XGenericFieldParser;

public class AnnotationUtils {

	@SuppressWarnings("unchecked")
	public static <T> XAnnotationField<T> create(final String name,
			final T value) {
		if (value == null) {
			return null;
		} else {

			try {
				return XGenericFieldParser.GENERIC.construct(name, value,
						value.getClass());
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
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
			return new XArrayAnnotationField<Annotation>(name,
					Annotation[].class, values);
		}
	}

	public static <T> XAnnotationField<T[]> create(final String name,
			final T[] value) {
		if (value == null) {
			return null;
		} else {
			try {
				@SuppressWarnings("unchecked")
				final XAnnotationFieldParser<T[], T[]> parser = XGenericFieldParser.GENERIC;
				return parser.construct(name, value, value.getClass());
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

}
