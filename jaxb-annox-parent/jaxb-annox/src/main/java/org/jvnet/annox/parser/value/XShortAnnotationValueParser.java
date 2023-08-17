package org.jvnet.annox.parser.value;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XShortAnnotationValue;
import org.jvnet.annox.parser.exception.ValueParseException;
import org.jvnet.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.annox.parser.java.visitor.ShortExpressionVisitor;

public class XShortAnnotationValueParser extends
		XAnnotationValueParser<Short, Short> {

	@Override
	public XAnnotationValue<Short> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(Short.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<Short> construct(Short value, Class<?> type) {
		return new XShortAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Short>> createExpressionVisitor(
			Class<?> type) {
		return new ShortExpressionVisitor(type);
	}

}
