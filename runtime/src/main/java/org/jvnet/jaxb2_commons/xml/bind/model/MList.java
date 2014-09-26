package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MList<T, C> extends MTypeInfo<T, C> {

	public MTypeInfo<T, C> getItemTypeInfo();

}
