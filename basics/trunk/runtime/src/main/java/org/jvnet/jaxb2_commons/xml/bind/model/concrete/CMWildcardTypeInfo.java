package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MWildcardTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MWildcardTypeInfoOrigin;

public class CMWildcardTypeInfo implements MWildcardTypeInfo {

	private final MWildcardTypeInfoOrigin origin;

	public CMWildcardTypeInfo(MWildcardTypeInfoOrigin origin) {
		Validate.notNull(origin);
		this.origin = origin;
	}

	public MWildcardTypeInfoOrigin getOrigin() {
		return origin;
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitWildcardTypeInfo(this);
	}

}
