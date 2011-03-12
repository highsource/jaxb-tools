package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MPackagedTypeInfo<T, C> extends MTypeInfo<T, C>, MPackaged {

	public String getName();

	public String getLocalName();
}
