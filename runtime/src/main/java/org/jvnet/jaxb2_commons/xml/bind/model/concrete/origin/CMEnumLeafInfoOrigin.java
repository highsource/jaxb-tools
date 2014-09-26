package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;

import com.sun.xml.bind.v2.model.core.EnumLeafInfo;

public class CMEnumLeafInfoOrigin<T, C, ELI extends EnumLeafInfo<T, C>>
		implements MEnumLeafInfoOrigin, EnumLeafInfoOrigin<T, C, ELI> {

	private final ELI source;

	public CMEnumLeafInfoOrigin(ELI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public ELI getSource() {
		return source;
	}

	public MElementInfoOrigin createElementInfoOrigin() {
		return new CMEnumElementInfoOrigin<T, C, ELI>(
				getSource());
	}

}
