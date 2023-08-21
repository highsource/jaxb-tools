package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XStringAnnotationValue;

import japa.parser.ast.expr.StringLiteralExpr;

public final class StringExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<String>> {
	public StringExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<String> visitDefault(StringLiteralExpr n, Void arg) {
		return new XStringAnnotationValue(String.valueOf(n.getValue()));
	}
}