package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XLongAnnotationValue;

public final class LongExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Long>> {
	public LongExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Long> visitDefault(LiteralStringValueExpr n, Void arg) {
		return new XLongAnnotationValue(Long.valueOf(n.getValue()));
	}

    @Override
    public XAnnotationValue<Long> visit(UnaryExpr n, Void arg) {
        if (n.getExpression() instanceof LiteralStringValueExpr && n.getOperator() == UnaryExpr.Operator.MINUS) {
            return visit(new LongLiteralExpr("-" + ((LiteralStringValueExpr) n.getExpression()).getValue()), arg);
        }
        return super.visit(n, arg);
    }

	@Override
	public XAnnotationValue<Long> visit(LongLiteralExpr n, Void arg) {
		String value = n.getValue();
		if (value.endsWith("L") || value.endsWith("l")) {
			return new XLongAnnotationValue(Long.valueOf(value.substring(0,
					value.length() - 1)));
		} else {
			return super.visit(n, arg);
		}
	}
}
