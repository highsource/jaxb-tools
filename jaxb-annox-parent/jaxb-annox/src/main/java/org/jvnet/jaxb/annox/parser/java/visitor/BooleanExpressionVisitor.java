package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XBooleanAnnotationValue;

import com.github.javaparser.ast.expr.BooleanLiteralExpr;

public final class BooleanExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Boolean>> {
	public BooleanExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Boolean> visit(BooleanLiteralExpr n, Void arg) {
		return new XBooleanAnnotationValue(n.getValue());
	}
}
