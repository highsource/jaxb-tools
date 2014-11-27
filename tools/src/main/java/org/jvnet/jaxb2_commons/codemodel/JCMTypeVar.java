package org.jvnet.jaxb2_commons.codemodel;

import com.sun.codemodel.JTypeVar;

public class JCMTypeVar extends JCMType<JTypeVar> {

	public JCMTypeVar(JCMTypeFactory factory, JTypeVar type) {
		super(factory, type);
	}

	@Override
	public <V> V accept(JCMTypeVisitor<V> visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean matches(JCMType<?> type) {
		return type.accept(matchesTypeVisitor);
	}

	private final JCMTypeVisitor<Boolean> matchesTypeVisitor = new JCMTypeVisitor<Boolean>() {
		@Override
		public Boolean visit(JCMClass type) {
			return getType().isAssignableFrom(type.getType());
		}

		@Override
		public Boolean visit(JCMNullType type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMPrimitiveType type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMTypeVar type) {
			return getType().isAssignableFrom(type.getType());
		}
	};

}
