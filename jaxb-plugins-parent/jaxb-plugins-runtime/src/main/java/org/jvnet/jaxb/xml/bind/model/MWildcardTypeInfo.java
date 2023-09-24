package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MOriginated;
import org.jvnet.jaxb.xml.bind.model.origin.MWildcardTypeInfoOrigin;

public interface MWildcardTypeInfo<T, C extends T> extends MTypeInfo<T, C>,
		MOriginated<MWildcardTypeInfoOrigin> {

}
