package org.jvnet.jaxb.annox.parser.java.visitor;

import japa.parser.ast.Node;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.japa.parser.ast.visitor.AbstractGenericExpressionVisitor;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;

public class ExpressionVisitor<V> extends
		AbstractGenericExpressionVisitor<V, Void> {
	protected final Class<?> targetClass;

	public ExpressionVisitor(Class<?> targetClass) {
		Validate.notNull(targetClass);
		this.targetClass = targetClass;
	}

	@Override
	public V visitDefault(Node n, Void arg) {
		throw new RuntimeException(new ValueParseException(n,
				this.targetClass));
	}

}