package org.jvnet.annox.parser.java.visitor;

import japa.parser.ast.expr.StringLiteralExpr;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XDoubleAnnotationValue;

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