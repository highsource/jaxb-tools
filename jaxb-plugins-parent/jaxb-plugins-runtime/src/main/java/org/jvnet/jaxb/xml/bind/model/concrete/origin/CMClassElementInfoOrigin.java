package org.jvnet.jaxb.xml.bind.model.concrete.origin;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.origin.MElementInfoOrigin;
import org.glassfish.jaxb.core.v2.model.core.ClassInfo;

public class CMClassElementInfoOrigin<T, C, CI extends ClassInfo<T, C>>
		implements MElementInfoOrigin, ClassInfoOrigin<T, C, CI> {

	private final CI source;

	public CMClassElementInfoOrigin(CI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public CI getSource() {
		return source;
	}

}
