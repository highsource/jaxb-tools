package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XIntAnnotationValue;

import japa.parser.ast.expr.StringLiteralExpr;

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