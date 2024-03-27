package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XByteAnnotationValue;

import com.github.javaparser.ast.expr.StringLiteralExpr;

public final class ByteExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Byte>> {
	public ByteExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Byte> visitDefault(StringLiteralExpr n, Void arg) {
		return new XByteAnnotationValue(Byte.valueOf(n.getValue()));
	}
}
