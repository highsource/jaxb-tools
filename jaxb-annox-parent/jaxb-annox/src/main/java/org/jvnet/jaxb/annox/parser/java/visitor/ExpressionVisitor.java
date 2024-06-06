package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.Node;

import org.jvnet.jaxb.annox.javaparser.ast.visitor.AbstractGenericExpressionVisitor;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.util.Validate;

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
