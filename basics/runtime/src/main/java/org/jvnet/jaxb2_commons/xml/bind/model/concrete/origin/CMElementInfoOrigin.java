package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

import com.sun.xml.bind.v2.model.core.ElementInfo;

public class CMElementInfoOrigin<T, C, EI extends ElementInfo<T, C>> implements
		MElementInfoOrigin, ElementInfoOrigin<T, C, EI> {

	private final EI source;

	public CMElementInfoOrigin(EI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public EI getSource() {
		return source;
	}

}
