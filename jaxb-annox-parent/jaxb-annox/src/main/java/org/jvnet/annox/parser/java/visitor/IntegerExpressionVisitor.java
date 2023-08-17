package org.jvnet.annox.parser.java.visitor;

import japa.parser.ast.expr.StringLiteralExpr;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XIntAnnotationValue;

public final class IntegerExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Integer>> {
	public IntegerExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Integer> visitDefault(StringLiteralExpr n, Void arg) {
		return new XIntAnnotationValue(Integer.valueOf(n.getValue()));
	}
}