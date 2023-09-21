package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MOriginated;

public interface MClassTypeInfo<T, C extends T, O> extends
		MPackagedTypeInfo<T, C>, MTypeInfo<T, C>, MOriginated<O>, MContainer {

	public C getTargetType();

	public <V> V acceptClassTypeInfoVisitor(
			MClassTypeInfoVisitor<T, C, V> visitor);

}
