package org.jvnet.jaxb.codemodel;

public interface JCMTypeVisitor<V> {

	public V visit(JCMClass type);

	public V visit(JCMNullType type);

	public V visit(JCMPrimitiveType type);

	public V visit(JCMTypeVar type);

	public V visit(JCMArrayClass type);

	public V visit(JCMTypeWildcard type);
}
