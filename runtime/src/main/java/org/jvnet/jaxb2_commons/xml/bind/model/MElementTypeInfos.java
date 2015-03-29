package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

public interface MElementTypeInfos<T, C extends T> {
	public List<MElementTypeInfo<T, C>> getElementTypeInfos();
}
