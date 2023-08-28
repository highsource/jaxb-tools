package org.jvnet.jaxb2_commons.codemodel;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JType;

// JArrayClass
public class JCMArrayClass extends JCMType<JClass> {

	private final JCMType<?> elementType;

	public JCMArrayClass(JCMTypeFactory factory, JClass type) {
		super(factory, type);
		elementType = factory.create(type.elementType());
	}

	public JCMType<?> getElementType() {
		return elementType;
	}

	@Override
	public JType getDeclarableType() {
		return getElementType().getDeclarableType().array();
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
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMTypeVar type) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean visit(JCMArrayClass type) {
			return getElementType().matches(type.getElementType());
		}

		@Override
		public Boolean visit(JCMTypeWildcard type) {
			return Boolean.FALSE;
		}

	};

	@Override
	public <V> V accept(JCMTypeVisitor<V> visitor) {
		return visitor.visit(this);
	}

}
