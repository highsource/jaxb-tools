package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MClassTypeInfo<T, C> extends MPackagedTypeInfo<T, C>,
		MContainer {

	public C getTargetClass();

	public <V> V acceptClassTypeInfoVisitor(
			MClassTypeInfoVisitor<T, C, V> visitor);

}
