package org.jvnet.annox.parser.java.visitor;

import japa.parser.ast.expr.StringLiteralExpr;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XFloatAnnotationValue;

public final class FloatExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Float>> {
	public FloatExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Float> visitDefault(StringLiteralExpr n, Void arg) {
		return new XFloatAnnotationValue(Float.valueOf(n.getValue()));
	}
}