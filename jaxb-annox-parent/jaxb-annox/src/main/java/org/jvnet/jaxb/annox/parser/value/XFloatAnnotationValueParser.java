package org.jvnet.jaxb.annox.parser.value;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XFloatAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.parser.java.visitor.FloatExpressionVisitor;

public class XFloatAnnotationValueParser extends
		XAnnotationValueParser<Float, Float> {

	@Override
	public XAnnotationValue<Float> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(Float.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<Float> construct(Float value, Class<?> type) {
		return new XFloatAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Float>> createExpressionVisitor(
			Class<?> type) {
		return new FloatExpressionVisitor(type);
	}

}
