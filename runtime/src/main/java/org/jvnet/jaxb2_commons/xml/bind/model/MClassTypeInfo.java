package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MClassTypeInfo<T, C extends T> extends
		MPackagedTypeInfo<T, C>, MTypeInfo<T, C>, MContainer {

	public C getTargetType();

	public <V> V acceptClassTypeInfoVisitor(
			MClassTypeInfoVisitor<T, C, V> visitor);

}
