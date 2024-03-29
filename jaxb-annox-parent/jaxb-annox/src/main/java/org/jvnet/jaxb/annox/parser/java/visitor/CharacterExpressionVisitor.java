package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XCharAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;

public final class CharacterExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Character>> {
	public CharacterExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Character> visitDefault(LiteralStringValueExpr n, Void arg) {
		final String value = n.getValue();
		if (value == null) {
			throw new RuntimeException(new ValueParseException(value, this.targetClass));
		} else if (value.length() != 1) {
			throw new RuntimeException(new ValueParseException(value, this.targetClass));
		} else {
			return new XCharAnnotationValue(Character.valueOf(value.charAt(0)));
		}
	}
}
