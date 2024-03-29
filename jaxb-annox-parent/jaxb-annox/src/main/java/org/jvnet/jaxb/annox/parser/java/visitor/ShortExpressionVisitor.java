package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.UnaryExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XShortAnnotationValue;

import com.github.javaparser.ast.expr.StringLiteralExpr;

public final class ShortExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Short>> {
	public ShortExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Short> visitDefault(StringLiteralExpr n, Void arg) {
		return new XShortAnnotationValue(Short.valueOf(n.getValue()));
	}

    @Override
    public XAnnotationValue<Short> visit(UnaryExpr n, Void arg) {
        if (n.getExpr() instanceof StringLiteralExpr && n.getOperator() == UnaryExpr.Operator.negative) {
            return visit(new StringLiteralExpr("-" + ((StringLiteralExpr) n.getExpr()).getValue()), arg);
        }
        return super.visit(n, arg);
    }
}
