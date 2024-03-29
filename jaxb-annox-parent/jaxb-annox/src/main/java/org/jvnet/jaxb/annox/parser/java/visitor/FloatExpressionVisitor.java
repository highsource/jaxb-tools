package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.UnaryExpr;
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

    @Override
    public XAnnotationValue<Float> visit(UnaryExpr n, Void arg) {
        if (n.getExpr() instanceof StringLiteralExpr && n.getOperator() == UnaryExpr.Operator.negative) {
            return visit(new StringLiteralExpr("-" + ((StringLiteralExpr) n.getExpr()).getValue()), arg);
        }
        return super.visit(n, arg);
    }
}
