package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XDoubleAnnotationValue;

public final class DoubleExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Double>> {
	public DoubleExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Double> visitDefault(LiteralStringValueExpr n, Void arg) {
		return new XDoubleAnnotationValue(Double.valueOf(n.getValue()));
	}

    @Override
    public XAnnotationValue<Double> visit(UnaryExpr n, Void arg) {
        if (n.getExpression() instanceof LiteralStringValueExpr && n.getOperator() == UnaryExpr.Operator.MINUS) {
            return visit(new DoubleLiteralExpr("-" + ((LiteralStringValueExpr) n.getExpression()).getValue()), arg);
        }
        return super.visit(n, arg);
    }
}
