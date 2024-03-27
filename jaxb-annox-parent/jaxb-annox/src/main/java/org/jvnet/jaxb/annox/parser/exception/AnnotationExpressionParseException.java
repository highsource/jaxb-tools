package org.jvnet.jaxb.annox.parser.exception;

import java.text.MessageFormat;

import com.github.javaparser.ast.Node;

public class AnnotationExpressionParseException extends Exception {

	private static final long serialVersionUID = 1L;

	private Node expression;

	public AnnotationExpressionParseException(Node expression, Throwable cause) {
		super(
				MessageFormat.format(
						"Could not parse the annotation expression [{0}].",
						expression), cause);
		this.expression = expression;
	}

	public Node getExpression() {
		return expression;
	}

}
