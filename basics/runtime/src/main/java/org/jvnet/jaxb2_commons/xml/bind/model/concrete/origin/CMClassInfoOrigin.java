package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

import com.sun.xml.bind.v2.model.core.ClassInfo;

public class CMClassInfoOrigin<T, C, CI extends ClassInfo<T, C>> implements
		MClassInfoOrigin, ClassInfoOrigin<T, C, CI> {

	private final CI source;

	public CMClassInfoOrigin(CI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public CI getSource() {
		return source;
	}

	public MElementInfoOrigin createElementInfoOrigin() {
		return new CMClassElementInfoOrigin<T, C, CI>(getSource());
	}

}
