package org.jvnet.jaxb2_commons.codemodel;

public class DefaultJCMTypeVisitor<V> implements JCMTypeVisitor<V> {

	public V defaultVisit(JCMType<?> type) {
		return null;
	}

	@Override
	public V visit(JCMClass type) {
		return defaultVisit(type);
	}

	@Override
	public V visit(JCMNullType type) {
		return defaultVisit(type);
	}

	@Override
	public V visit(JCMPrimitiveType type) {
		return defaultVisit(type);
	}

	@Override
	public V visit(JCMTypeVar type) {
		return defaultVisit(type);
	}
}
