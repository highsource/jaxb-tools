//package org.jvnet.jaxb.annox.parser;
//
//import com.github.javaparser.ast.Node;
//import com.github.javaparser.ast.expr.AnnotationExpr;
//import com.github.javaparser.ast.expr.Expression;
//
//import java.lang.annotation.Annotation;
//import java.text.MessageFormat;
//
//import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
//import visitor.ast.org.jvnet.jaxb.annox.javaparser.AbstractGenericExpressionVisitor;
//import org.jvnet.jaxb.annox.model.XAnnotation;
//import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
//import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
//import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XXAnnotationAnnotationValue;
//import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
//import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
//import org.jvnet.jaxb.annox.util.AnnotationElementUtils;
//import org.w3c.dom.Element;
//
//public class XXAnnotationSingleAnnotationFieldParser<A extends Annotation>
//		extends XAnnotationFieldParser<A, XAnnotation<A>> {
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public XAnnotationField<A> parse(Element annotationElement, String name,
//			Class<?> type) throws AnnotationElementParseException {
//
//		final Element element = AnnotationElementUtils.getFieldElement(
//				annotationElement, name);
//
//		if (element == null) {
//			return null;
//		} else {
//			try {
//				final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
//						.parse(element);
//
//				final XAnnotationValue<A> value = new XXAnnotationAnnotationValue<A>(
//						xannotation);
//				return new XSingleAnnotationField<A>(name, type, value);
//			} catch (AnnotationElementParseException aepex) {
//				throw new AnnotationElementParseException(annotationElement,
//						aepex);
//			}
//		}
//	}
//
//	@Override
//	public XAnnotationField<A> parse(Expression annotationElement, String name,
//			Class<?> type) throws AnnotationExpressionParseException {
//
//		final AnnotationExpr element = annotationElement.accept(
//				new AbstractGenericExpressionVisitor<AnnotationExpr, Void>() {
//					@Override
//					public AnnotationExpr visitDefault(Node n, Void arg) {
//						throw new IllegalArgumentException(
//								MessageFormat
//										.format("Unexpected expression [{0}], only annotation expressions are expected.",
//												n));
//					}
//
//					@Override
//					public AnnotationExpr visitDefault(AnnotationExpr n,
//							Void arg) {
//						return n;
//					}
//				}, null);
//
//		if (element == null) {
//			return null;
//		} else {
//			try {
//				@SuppressWarnings("unchecked")
//				final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
//						.parse(element);
//				final XAnnotationValue<A> value = new XXAnnotationAnnotationValue<A>(
//						xannotation);
//				return new XSingleAnnotationField<A>(name, type, value);
//			} catch (AnnotationExpressionParseException aepex) {
//				throw new AnnotationExpressionParseException(annotationElement,
//						aepex);
//			}
//		}
//	}
//
//	@Override
//	public XAnnotationField<A> parse(Annotation annotation, String name,
//			Class<?> type) throws NoSuchAnnotationFieldException {
//		final A value = getAnnotationFieldValue(annotation, name);
//		@SuppressWarnings("unchecked")
//		final XAnnotation<A> xannotation = (XAnnotation<A>) XAnnotationParser.INSTANCE
//				.parse(value);
//		return new XSingleAnnotationField<A>(name, type,
//				new XXAnnotationAnnotationValue<A>(value, xannotation));
//	}
//
//	@Override
//	public XAnnotationField<A> construct(String name,
//			XAnnotation<A> annotation, Class<?> type) {
//		return new XSingleAnnotationField<A>(name, type,
//				new XXAnnotationAnnotationValue<A>(annotation));
//	}
//
//}
