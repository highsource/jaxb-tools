package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementTypeRefOrigin;

import org.glassfish.jaxb.core.v2.model.core.ElementPropertyInfo;
import org.glassfish.jaxb.core.v2.model.core.TypeRef;

public class CMElementTypeRefOrigin<T, C, EPI extends ElementPropertyInfo<T, C>, TR extends TypeRef<T, C>>
		implements MElementTypeRefOrigin {

	private final EPI source;
	private final TR typeRef;

	public CMElementTypeRefOrigin(EPI source, TR typeRef) {
		Validate.notNull(source);
		Validate.notNull(typeRef);
		this.source = source;
		this.typeRef = typeRef;
	}

	public EPI getSource() {
		return source;
	}

	public TR getTypeRef() {
		return typeRef;
	}
}
