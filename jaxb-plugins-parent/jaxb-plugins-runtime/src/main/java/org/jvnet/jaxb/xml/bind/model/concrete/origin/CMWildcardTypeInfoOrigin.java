package org.jvnet.jaxb.xml.bind.model.concrete.origin;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.origin.MWildcardTypeInfoOrigin;
import org.glassfish.jaxb.core.v2.model.core.WildcardTypeInfo;

public class CMWildcardTypeInfoOrigin<T, C, WTI extends WildcardTypeInfo<T, C>>
		implements MWildcardTypeInfoOrigin, WildcardTypeInfoOrigin<T, C, WTI> {

	private final WTI source;

	public CMWildcardTypeInfoOrigin(WTI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public WTI getSource() {
		return source;
	}

}
