package org.jvnet.jaxb.annox.parser.value;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XLongAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.parser.java.visitor.LongExpressionVisitor;

public class XLongAnnotationValueParser extends
		XAnnotationValueParser<Long, Long> {

	@Override
	public XAnnotationValue<Long> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(Long.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<Long> construct(Long value, Class<?> type) {
		return new XLongAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Long>> createExpressionVisitor(
			Class<?> type) {
		return new LongExpressionVisitor(type);
	}

}
