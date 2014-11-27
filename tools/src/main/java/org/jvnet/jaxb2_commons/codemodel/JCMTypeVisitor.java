package org.jvnet.jaxb2_commons.codemodel;

public interface JCMTypeVisitor<V> {

	public V visit(JCMClass type);

	public V visit(JCMNullType type);

	public V visit(JCMPrimitiveType type);

	public V visit(JCMTypeVar type);

}
