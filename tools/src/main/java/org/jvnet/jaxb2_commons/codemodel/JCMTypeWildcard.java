package org.jvnet.jaxb2_commons.codemodel;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JType;

public class JCMTypeWildcard extends JCMType<JClass> {

	private final JCMType<? extends JClass> boundType;
	
	public JCMTypeWildcard(JCMTypeFactory factory, JClass type) {
		super(factory, type);
		boundType = factory.create(type._extends());
	}
	
	public JCMType<? extends JClass> getBoundType() {
		return boundType;
	}
	
	@Override
	public JType getDeclarableType() {
		return getBoundType().getDeclarableType();
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

		@Override
		public Boolean visit(JCMArrayClass type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMTypeWildcard type) {
			return getType().isAssignableFrom(type.getType());
		}
	};
}
