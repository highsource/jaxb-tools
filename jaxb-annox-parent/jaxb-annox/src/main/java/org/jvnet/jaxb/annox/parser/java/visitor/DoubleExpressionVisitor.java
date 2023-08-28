package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XDoubleAnnotationValue;

import japa.parser.ast.expr.StringLiteralExpr;

public final class DoubleExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Double>> {
	public DoubleExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Double> visitDefault(StringLiteralExpr n, Void arg) {
		return new XDoubleAnnotationValue(Double.valueOf(n.getValue()));
	}
}
