package org.jvnet.jaxb.annox.parser;

import japa.parser.ast.expr.Expression;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.parser.value.XAnnotationValueParser;
import org.jvnet.jaxb.annox.util.AnnotationElementUtils;
import org.w3c.dom.Element;

public class XSingleAnnotationFieldParser<T, V> extends
		XAnnotationFieldParser<T, V> {

	private final XAnnotationValueParser<T, V> annotationValueParser;
	
	public XSingleAnnotationFieldParser(
			XAnnotationValueParser<T, V> annotationValueParser) {
		Validate.notNull(annotationValueParser);
		this.annotationValueParser = annotationValueParser;
	}

	@Override
	public XAnnotationField<T> parse(Element element, String name, Class<?> type)
			throws AnnotationElementParseException {
		Validate.notNull(element, "Element must not be null.");
		Validate.notNull(name, "Field name must not be null.");
		Validate.notNull(type, "Type must not be null.");
		final String draft = AnnotationElementUtils
				.getFieldValue(element, name);

		if (draft == null) {
			return null;
		} else {
			try {
				final XAnnotationValue<T> value = this.annotationValueParser.parse(draft, type);
				return new XSingleAnnotationField<T>(name, type, value);
			} catch (ValueParseException vpex) {
				throw new AnnotationElementParseException(element, vpex);
			}
		}
	}

	public XAnnotationField<T> parse(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {
		final V value = this.<V> getAnnotationFieldValue(annotation, name);
		return construct(name, value, type);
	}

	public final XAnnotationField<T> construct(String name, V value,
			Class<?> type) {
		final XAnnotationValue<T> fieldValue = this.annotationValueParser.construct(value, type);
		return new XSingleAnnotationField<T>(name, type, fieldValue);
	}

	@Override
	public XAnnotationField<T> parse(Expression expression, String name,
			Class<?> type) throws AnnotationExpressionParseException {
		final ExpressionVisitor<XAnnotationValue<T>> expressionVisitor = this.annotationValueParser
				.createExpressionVisitor(type);
		try {

			final XAnnotationValue<T> value = expression.accept(
					expressionVisitor, null);
			return new XSingleAnnotationField<T>(name, type, value);
		} catch (RuntimeException rex) {
			if (rex.getCause() != null) {
				throw new AnnotationExpressionParseException(expression,
						rex.getCause());
			} else {
				throw new AnnotationExpressionParseException(expression, rex);
			}
		}
	}
}
