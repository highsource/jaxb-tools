package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XByteAnnotationValue;

public final class ByteExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Byte>> {
	public ByteExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Byte> visitDefault(LiteralStringValueExpr n, Void arg) {
		return new XByteAnnotationValue(Byte.valueOf(n.getValue()));
	}
}
