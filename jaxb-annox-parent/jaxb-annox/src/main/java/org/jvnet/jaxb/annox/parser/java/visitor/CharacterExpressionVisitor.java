package org.jvnet.jaxb.annox.parser.java.visitor;

import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XCharAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;

import japa.parser.ast.expr.StringLiteralExpr;

public final class CharacterExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Character>> {
	public CharacterExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Character> visitDefault(StringLiteralExpr n,
			Void arg) {
		final String value = n.getValue();
		if (value == null) {
			throw new RuntimeException(new ValueParseException(value,
					this.targetClass));
		} else if (value.length() != 1) {
			throw new RuntimeException(new ValueParseException(value,
					this.targetClass));
		} else {
			return new XCharAnnotationValue(Character.valueOf(value.charAt(0)));
		}
	}
}