package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MElementTypeRefOrigin;

public interface MElementsPropertyInfo<T, C extends T> extends
		MPropertyInfo<T, C>, MWrappable,
		MElementTypeInfos<T, C, MElementTypeRef<T, C>, MElementTypeRefOrigin> {

}
