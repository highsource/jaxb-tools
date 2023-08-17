package org.jvnet.annox.parser.value;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XDoubleAnnotationValue;
import org.jvnet.annox.parser.exception.ValueParseException;
import org.jvnet.annox.parser.java.visitor.DoubleExpressionVisitor;
import org.jvnet.annox.parser.java.visitor.ExpressionVisitor;

public class XDoubleAnnotationValueParser extends
		XAnnotationValueParser<Double, Double> {

	@Override
	public XAnnotationValue<Double> parse(String value, Class<?> type)
			throws ValueParseException {
		return construct(Double.valueOf(value), type);
	}

	@Override
	public XAnnotationValue<Double> construct(Double value, Class<?> type) {
		return new XDoubleAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Double>> createExpressionVisitor(
			Class<?> type) {
		return new DoubleExpressionVisitor(type);
	}

}
