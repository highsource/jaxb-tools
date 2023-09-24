package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MElementOrigin;

public interface MElementRefsPropertyInfo<T, C extends T> extends
		MPropertyInfo<T, C>, MMixable, MWrappable, MWildcard,
		MElementTypeInfos<T, C, MElement<T, C>, MElementOrigin> {

}
