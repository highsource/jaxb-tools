package org.jvnet.annox.parser.value;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XBooleanAnnotationValue;
import org.jvnet.annox.parser.exception.ValueParseException;
import org.jvnet.annox.parser.java.visitor.BooleanExpressionVisitor;
import org.jvnet.annox.parser.java.visitor.ExpressionVisitor;

public class XBooleanAnnotationValueParser extends
		XAnnotationValueParser<Boolean, Boolean> {

	@Override
	public XAnnotationValue<Boolean> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(Boolean.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<Boolean> construct(Boolean value, Class<?> type) {
		return new XBooleanAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Boolean>> createExpressionVisitor(
			Class<?> type) {
		return new BooleanExpressionVisitor(type);
	}

}
