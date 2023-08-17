package org.jvnet.annox.parser.value;

import org.jvnet.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.annox.model.annotation.value.XCharAnnotationValue;
import org.jvnet.annox.parser.exception.ValueParseException;
import org.jvnet.annox.parser.java.visitor.CharacterExpressionVisitor;
import org.jvnet.annox.parser.java.visitor.ExpressionVisitor;

public class XCharAnnotationValueParser extends
		XAnnotationValueParser<Character, Character> {

	@Override
	public XAnnotationValue<Character> parse(String value, Class<?> type)
			throws ValueParseException {
		if (value.length() != 1) {
			throw new ValueParseException(value, type);
		}
		return construct(Character.valueOf(value.charAt(0)), type);
	}

	@Override
	public XAnnotationValue<Character> construct(Character value, Class<?> type) {
		return new XCharAnnotationValue(value);
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Character>> createExpressionVisitor(
			Class<?> type) {
		return new CharacterExpressionVisitor(type);
	}

}
