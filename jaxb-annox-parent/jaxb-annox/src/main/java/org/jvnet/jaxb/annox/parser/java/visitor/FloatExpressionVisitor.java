package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XFloatAnnotationValue;

import com.github.javaparser.ast.expr.StringLiteralExpr;

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
