package org.jvnet.annox.parser;

import japa.parser.ast.expr.Expression;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jvnet.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.annox.parser.exception.AnnotationExpressionParseException;
import org.w3c.dom.Element;

public abstract class XAnnotationFieldParser<T, V> {

	public abstract XAnnotationField<T> parse(Element element, String name,
			Class<?> type) throws AnnotationElementParseException;

	public abstract XAnnotationField<T> parse(Expression expression,
			String name, Class<?> type)
			throws AnnotationExpressionParseException;

	public abstract XAnnotationField<T> parse(Annotation annotation,
			String name, Class<?> type) throws NoSuchAnnotationFieldException;

	public abstract XAnnotationField<T> construct(String name, V value,
			Class<?> type);

	@SuppressWarnings("unchecked")
	public <U> U getAnnotationFieldValue(Annotation annotation, String name)
			throws NoSuchAnnotationFieldException {
		final Class<? extends Annotation> annotationClass = annotation
				.annotationType();
		try {
			final Method method = annotationClass.getMethod(name);
			final U value = (U) method.invoke(annotation);
			return value;
		} catch (NoSuchMethodException nsmex) {
			throw new NoSuchAnnotationFieldException(annotationClass, name,
					nsmex);
		} catch (IllegalAccessException iaex) {
			throw new AssertionError(iaex);
		} catch (InvocationTargetException itex) {
			throw new AssertionError(itex);
		}
	}
}
