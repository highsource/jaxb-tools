package org.jvnet.jaxb.annox.parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;

import java.lang.annotation.Annotation;

import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.javaparser.ast.visitor.AbstractGenericExpressionVisitor;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XXAnnotationAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.util.AnnotationElementUtils;
import org.w3c.dom.Element;

public class XAnnotationSingleAnnotationFieldParser<A extends Annotation>
		extends XAnnotationFieldParser<A, A> {

	@Override
	@SuppressWarnings("unchecked")
	public XAnnotationField<A> parse(Element annotationElement, String name,
			Class<?> type) throws AnnotationElementParseException {

		final Element element = AnnotationElementUtils.getFieldElement(
				annotationElement, name);

		if (element == null) {
			return null;
		} else {
			try {
				final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
						.parse(element);
				return construct(name, xannotation, type);
			} catch (AnnotationElementParseException aepex) {
				throw new AnnotationElementParseException(annotationElement,
						aepex);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public XAnnotationField<A> parse(Expression annotationElement, String name,
			Class<?> type) throws AnnotationExpressionParseException {

		final AnnotationExpr element = annotationElement.accept(
				new AbstractGenericExpressionVisitor<AnnotationExpr, Void>() {

					public AnnotationExpr visitDefault(AnnotationExpr n,
							Void arg) {
						return n;
					}

					@Override
					public AnnotationExpr visitDefault(Node n, Void arg) {
						throw new IllegalArgumentException("Expression [" + n
								+ "] must be an annotation expression.");
					}
				}, null);

		if (element == null) {
			return null;
		} else {
			try {
				final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
						.parse(element);
				return construct(name, xannotation, type);
			} catch (AnnotationExpressionParseException aepex) {
				throw new AnnotationExpressionParseException(annotationElement,
						aepex);
			}
		}
	}

	@Override
	public XAnnotationField<A> parse(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {
		final A value = getAnnotationFieldValue(annotation, name);
		return construct(name, value, type);
	}

	@Override
	public XAnnotationField<A> construct(String name, A value, Class<?> type) {
		@SuppressWarnings("unchecked")
		final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
				.parse(value);
		return new XSingleAnnotationField<A>(name, type,
				new XXAnnotationAnnotationValue<A>(value, xannotation));
	}

	private XAnnotationField<A> construct(String name,
			XAnnotation<A> xannotation, Class<?> type) {
		return new XSingleAnnotationField<A>(name, type,
				new XXAnnotationAnnotationValue<A>(xannotation));
	}
}
