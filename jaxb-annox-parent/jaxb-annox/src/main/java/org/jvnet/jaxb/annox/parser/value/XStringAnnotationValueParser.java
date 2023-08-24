package org.jvnet.jaxb.annox.parser.value;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XStringAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.parser.java.visitor.StringExpressionVisitor;

public class XStringAnnotationValueParser extends
		XAnnotationValueParser<String, String> {

	@Override
	public XAnnotationValue<String> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(String.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<String> construct(String value, Class<?> type) {
		return new XStringAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<String>> createExpressionVisitor(
			Class<?> type) {
		return new StringExpressionVisitor(type);
	}

}
