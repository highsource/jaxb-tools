package org.jvnet.jaxb2_commons.codemodel;

import com.sun.codemodel.JClass;

public class JCMClass extends JCMType<JClass> {

	public JCMClass(JCMTypeFactory factory, JClass type) {
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
	
	private boolean matches(final JClass thatType) {
		final JClass thisType = getType();
		if (thisType.isAssignableFrom(thatType))
		{
			return true;
		}
		else if(thisType.erasure().isAssignableFrom(thatType.erasure()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private final JCMTypeVisitor<Boolean> matchesTypeVisitor = new JCMTypeVisitor<Boolean>()
	{
		@Override
		public Boolean visit(JCMClass type) {
			return matches(type.getType());
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
			return matches(type.getType());
		}
	};
}
