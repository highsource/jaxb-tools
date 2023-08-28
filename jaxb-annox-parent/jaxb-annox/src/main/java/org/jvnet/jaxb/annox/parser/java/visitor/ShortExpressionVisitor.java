package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XShortAnnotationValue;

import japa.parser.ast.expr.StringLiteralExpr;

public final class ShortExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Short>> {
	public ShortExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Short> visitDefault(StringLiteralExpr n, Void arg) {
		return new XShortAnnotationValue(Short.valueOf(n.getValue()));
	}
}
