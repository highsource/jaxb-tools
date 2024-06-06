package org.jvnet.jaxb.annox.parser;

import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.List;

import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.parser.value.XAnnotationValueParser;
import org.jvnet.jaxb.annox.util.AnnotationElementUtils;
import org.jvnet.jaxb.annox.util.ClassUtils;
import org.jvnet.jaxb.annox.util.Validate;
import org.w3c.dom.Element;

public class XArrayAnnotationFieldParser<T, V> extends
		XAnnotationFieldParser<T[], V[]> {

	private final XAnnotationValueParser<T, V> annotationValueParser;

	public XArrayAnnotationFieldParser(
			XAnnotationValueParser<T, V> annotationValueParser) {
		this.annotationValueParser = Validate.notNull(annotationValueParser);
	}

	@Override
	public XAnnotationField<T[]> parse(Element element, String name,
			Class<?> type) throws AnnotationElementParseException {
		final Class<?> componentType = type.getComponentType();
		final String[] draft = AnnotationElementUtils.getFieldValues(element,
				name);
		try {
			@SuppressWarnings("unchecked")
			final XAnnotationValue<T>[] fieldValues = (XAnnotationValue<T>[]) new XAnnotationValue[draft.length];

			for (int index = 0; index < draft.length; index++) {
				fieldValues[index] = this.annotationValueParser.parse(
						draft[index], componentType);
			}
			return new XArrayAnnotationField<T>(name, type, fieldValues);
		} catch (ValueParseException vpex) {
			throw new AnnotationElementParseException(element, vpex);
		}
	}

	@Override
	public XAnnotationField<T[]> construct(String name, V[] value, Class<?> type) {
		if (!type.isArray()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Type [{0}] is expected to be an array type.", type));
		}
		final Class<?> componentType = type.getComponentType();
		@SuppressWarnings("unchecked")
		final XAnnotationValue<T>[] fieldValues = (XAnnotationValue<T>[]) new XAnnotationValue[value.length];
		for (int index = 0; index < value.length; index++) {
			final V item = value[index];
			fieldValues[index] = this.annotationValueParser.construct(item,
					componentType);
		}
		final XArrayAnnotationField<T> annotationField = new XArrayAnnotationField<T>(
				name, type, fieldValues);
		return annotationField;
	}

	@SuppressWarnings("unchecked")
	@Override
	public XAnnotationField<T[]> parse(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {
		final Object values = getAnnotationFieldValue(annotation, name);
		final int length = Array.getLength(values);

		final V[] value = (V[]) Array.newInstance(
				ClassUtils.primitiveToWrapper(type.getComponentType()), length);
		for (int index = 0; index < length; index++) {
			final Object object = Array.get(values, index);
			value[index] = (V) object;
		}
		return construct(name, value, type);
	}

	@Override
	public XAnnotationField<T[]> parse(Expression expression,
			final String name, final Class<?> type)
			throws AnnotationExpressionParseException {
		final ExpressionVisitor<XAnnotationValue<T>> expressionVisitor = this.annotationValueParser
				.createExpressionVisitor(type.getComponentType());
		try {
			return expression.accept(
					new ExpressionVisitor<XAnnotationField<T[]>>(type) {

						@Override
						public XAnnotationField<T[]> visitDefault(Expression n, Void arg) {
							final XAnnotationValue<T> v = n.accept(expressionVisitor, null);
							@SuppressWarnings({ "unchecked", "rawtypes" })
							final XArrayAnnotationField<T> arrayAnnotationField = new XArrayAnnotationField(
									name, type, new XAnnotationValue[]{v});
							return arrayAnnotationField;
						}

						@Override
						public XAnnotationField<T[]> visit(
								ArrayInitializerExpr n, Void arg) {
							final List<Expression> expressions = n.getValues();

							@SuppressWarnings("unchecked")
							final XAnnotationValue<T>[] values = new XAnnotationValue[expressions
									.size()];

							for (int index = 0; index < expressions.size(); index++) {
								final XAnnotationValue<T> v = expressions.get(
										index).accept(expressionVisitor, null);
								values[index] = v;
							}
							@SuppressWarnings({ "unchecked", "rawtypes" })
							final XArrayAnnotationField<T> arrayAnnotationField = new XArrayAnnotationField(
									name, type, values);
							return arrayAnnotationField;
						}

					}, null);
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
