package org.jvnet.jaxb2_commons.codemodel;

import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JType;

public class JCMPrimitiveType extends JCMType<JPrimitiveType> {

	public JCMPrimitiveType(JCMTypeFactory factory, JPrimitiveType type) {
		super(factory, type);
	}

	@Override
	public JType getDeclarableType() {
		return getType();
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
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMNullType type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMPrimitiveType type) {
			return getType() == type.getType()
					|| getType().fullName().equals(type.getType().fullName());
		}

		@Override
		public Boolean visit(JCMTypeVar type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMArrayClass type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMTypeWildcard type) {
			return Boolean.FALSE;
		}
	};
}
