package org.jvnet.annox.parser.value;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.parser.exception.ValueParseException;
import org.jvnet.annox.parser.java.visitor.ExpressionVisitor;

public abstract class XAnnotationValueParser<T, V> {

	public abstract XAnnotationValue<T> parse(String value, Class<?> type)
			throws ValueParseException;

	public abstract XAnnotationValue<T> construct(V value, Class<?> type);

	public abstract ExpressionVisitor<XAnnotationValue<T>> createExpressionVisitor(
			Class<?> type);

}
