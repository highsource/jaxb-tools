package org.jvnet.jaxb.annox.parser.value;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XEnumAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.EnumExpressionVisitor;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;

public class XEnumAnnotationValueParser extends
		XAnnotationValueParser<Enum<?>, Enum<?>> {

	@Override
	public XAnnotationValue<Enum<?>> parse(String value, Class<?> type)
			throws ValueParseException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Enum> enumClass = (Class<? extends Enum>) type;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final XAnnotationValue<Enum<?>> annotationValue = new XEnumAnnotationValue(
				Enum.valueOf(enumClass, value));
		return annotationValue;
	}

	@Override
	public XAnnotationValue<Enum<?>> construct(Enum<?> value, Class<?> type) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final XAnnotationValue<Enum<?>> annotationValue = new XEnumAnnotationValue(
				value);
		return annotationValue;
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Enum<?>>> createExpressionVisitor(
			Class<?> type) {
		return new EnumExpressionVisitor(type);
	}

}
