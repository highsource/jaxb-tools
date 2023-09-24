package org.jvnet.jaxb.xml.bind.model;

import java.util.List;

public interface MElementTypeInfos<T, C extends T, M extends MElementTypeInfo<T, C, O>, O> {
	public List<M> getElementTypeInfos();
}
