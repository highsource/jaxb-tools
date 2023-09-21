package org.jvnet.jaxb.xml.bind.model;

public interface MPackagedTypeInfo<T, C extends T> extends MTypeInfo<T, C>,
		MPackaged, MContained {

	public String getName();

	public String getLocalName();
}
