package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XShortAnnotationValue;

public final class ShortExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Short>> {
	public ShortExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Short> visitDefault(LiteralStringValueExpr n, Void arg) {
		return new XShortAnnotationValue(Short.valueOf(n.getValue()));
	}

    @Override
    public XAnnotationValue<Short> visit(UnaryExpr n, Void arg) {
        if (n.getExpression() instanceof LiteralStringValueExpr && n.getOperator() == UnaryExpr.Operator.MINUS) {
            return visit(new StringLiteralExpr("-" + ((LiteralStringValueExpr) n.getExpression()).getValue()), arg);
        }
        return super.visit(n, arg);
    }
}
