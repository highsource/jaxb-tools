package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MPropertyInfoOrigin;

public interface MElementRefPropertyInfo<T, C extends T> extends MPropertyInfo<T, C>,
		MMixable, MWrappable, MWildcard, MElementTypeInfo<T, C, MPropertyInfoOrigin> {

}
