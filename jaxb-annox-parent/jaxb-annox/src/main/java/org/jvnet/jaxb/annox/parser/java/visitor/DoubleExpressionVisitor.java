package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XDoubleAnnotationValue;

import com.github.javaparser.ast.expr.StringLiteralExpr;

public final class DoubleExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Double>> {
	public DoubleExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Double> visitDefault(StringLiteralExpr n, Void arg) {
		return new XDoubleAnnotationValue(Double.valueOf(n.getValue()));
	}

    @Override
    public XAnnotationValue<Double> visit(UnaryExpr n, Void arg) {
        if (n.getExpr() instanceof StringLiteralExpr && n.getOperator() == UnaryExpr.Operator.negative) {
            return visit(new DoubleLiteralExpr("-" + ((StringLiteralExpr) n.getExpr()).getValue()), arg);
        }
        return super.visit(n, arg);
    }
}
