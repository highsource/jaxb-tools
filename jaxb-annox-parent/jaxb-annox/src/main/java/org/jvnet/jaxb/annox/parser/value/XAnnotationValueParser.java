package org.jvnet.jaxb.annox.parser.value;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;

public abstract class XAnnotationValueParser<T, V> {

	public abstract XAnnotationValue<T> parse(String value, Class<?> type)
			throws ValueParseException;

	public abstract XAnnotationValue<T> construct(V value, Class<?> type);

	public abstract ExpressionVisitor<XAnnotationValue<T>> createExpressionVisitor(
			Class<?> type);

}
