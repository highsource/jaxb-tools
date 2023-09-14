package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;

import org.glassfish.jaxb.core.v2.model.core.TypeInfoSet;

public class CMModelInfoOrigin<T, C, TIS extends TypeInfoSet<T, C, ?, ?>>
		implements MModelInfoOrigin, TypeInfoSetOrigin<T, C, TIS> {

	private final TIS source;

	public CMModelInfoOrigin(TIS source) {
		Validate.notNull(source);
		this.source = source;
	}

	public TIS getSource() {
		return source;
	}

}
