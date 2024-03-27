package org.jvnet.jaxb.annox.parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.List;

import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.javaparser.ast.visitor.AbstractGenericExpressionVisitor;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XXAnnotationAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.util.AnnotationElementUtils;
import org.w3c.dom.Element;

public class XAnnotationArrayAnnotationFieldParser<A extends Annotation>
		extends XAnnotationFieldParser<A[], A[]> {

	@Override
	public XAnnotationField<A[]> parse(Element annotationElement, String name,
			Class<?> type) throws AnnotationElementParseException {

		final Element[] elements = AnnotationElementUtils.getFieldElements(
				annotationElement, name);

		@SuppressWarnings("unchecked")
		final XAnnotation<A>[] xannotations = new XAnnotation[elements.length];

		for (int index = 0; index < elements.length; index++) {
			try {
				@SuppressWarnings("unchecked")
				final XAnnotation<A> annotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
						.parse(elements[index]);
				xannotations[index] = annotation;
			} catch (AnnotationElementParseException aepex) {
				throw new AnnotationElementParseException(annotationElement,
						aepex);
			}
		}
		return construct(name, xannotations, type);
	}

	public XAnnotationField<A[]> parse(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {
		final A[] value = getAnnotationFieldValue(annotation, name);
		@SuppressWarnings("unchecked")
		final XAnnotation<A>[] xannotations = new XAnnotation[value.length];

		for (int index = 0; index < value.length; index++) {
			@SuppressWarnings("unchecked")
			final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
					.parse(value[index]);
			xannotations[index] = xannotation;
		}
		return construct(name, xannotations, type);
	}

	@Override
	public XAnnotationField<A[]> parse(Expression expression, String name,
			Class<?> type) throws AnnotationExpressionParseException {
		final Expression[] items = expression.accept(
				new AbstractGenericExpressionVisitor<Expression[], Void>() {
					@Override
					public Expression[] visit(ArrayInitializerExpr n, Void arg) {
						final List<Expression> values = n.getValues();
						return values.toArray(new Expression[values.size()]);
					}

					@Override
					public Expression[] visitDefault(Expression n, Void arg) {
						return new Expression[]{n};
					}

					@Override
					public Expression[] visitDefault(Node n, Void arg) {
						throw new IllegalArgumentException(
								MessageFormat
										.format("Unexpected expression [{0}], only array initializer expressions are expected.",
												n));
					}
				}, null);

		@SuppressWarnings("unchecked")
		final XAnnotation<A>[] xannotations = new XAnnotation[items.length];

		for (int index = 0; index < items.length; index++) {

			final Expression item = items[index];
			final AnnotationExpr annotationExpression = item
					.accept(new AbstractGenericExpressionVisitor<AnnotationExpr, Void>() {
						@Override
						public AnnotationExpr visitDefault(Node n, Void arg) {
							throw new IllegalArgumentException(
									MessageFormat
											.format("Unexpected expression [{0}], only annotation expressions are expected.",
													n));
						}

						@Override
						public AnnotationExpr visitDefault(AnnotationExpr n,
								Void arg) {
							return n;
						}
					}, null);

			@SuppressWarnings("unchecked")
			final XAnnotation<A> annotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
					.parse(annotationExpression);
			xannotations[index] = annotation;
		}
		return construct(name, xannotations, type);
	}

	@SuppressWarnings("unchecked")
	public XAnnotationField<A[]> construct(String name, final A[] annotations,
			Class<?> type) {
		final XAnnotation<A>[] xannotations = new XAnnotation[annotations.length];
		final XAnnotationValue<A>[] xannotationValues = new XAnnotationValue[annotations.length];
		for (int index = 0; index < annotations.length; index++) {
			final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
					.parse(annotations[index]);
			xannotations[index] = xannotation;
			xannotationValues[index] = new XXAnnotationAnnotationValue<A>(
					annotations[index], xannotations[index]);
		}
		return new XArrayAnnotationField<A>(name, type, xannotationValues);
	}

	private XAnnotationField<A[]> construct(String name,
			final XAnnotation<A>[] xannotations, Class<?> type) {
		@SuppressWarnings("unchecked")
		final XAnnotationValue<A>[] xannotationValues = new XAnnotationValue[xannotations.length];
		for (int index = 0; index < xannotations.length; index++) {
			xannotationValues[index] = new XXAnnotationAnnotationValue<A>(
					xannotations[index]);
		}
		return new XArrayAnnotationField<A>(name, type, xannotationValues);
	}
}
