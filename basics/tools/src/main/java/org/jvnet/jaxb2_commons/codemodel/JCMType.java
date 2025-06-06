package org.jvnet.jaxb2_commons.codemodel;

import java.util.Objects;

import com.sun.codemodel.JType;

public abstract class JCMType<JT extends JType> {

	private final JCMTypeFactory factory;
	private final JT type;
	private final String fullName;

	public JCMType(JCMTypeFactory factory, JT type) {
		this.factory = Objects.requireNonNull(factory, "Type factory must not be null.");
		this.type = Objects.requireNonNull(type, "Type must not be null.");
		this.fullName = type.fullName();
	}

	public JCMTypeFactory getFactory() {
		return factory;
	}

	public JT getType() {
		return type;
	}

	public String getFullName() {
		return fullName;
	}

	public abstract JType getDeclarableType();

	public abstract boolean matches(JCMType<?> type);

	public abstract <V> V accept(JCMTypeVisitor<V> visitor);

	@Override
	public String toString() {
		return getType().toString();
	}
}
