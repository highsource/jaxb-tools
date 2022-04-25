package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;

import com.sun.xml.bind.v2.model.core.EnumLeafInfo;

public class CMEnumElementInfoOrigin<T, C, ELI extends EnumLeafInfo<T, C>>
		implements MElementInfoOrigin, EnumLeafInfoOrigin<T, C, ELI> {

	private final ELI source;

	public CMEnumElementInfoOrigin(ELI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public ELI getSource() {
		return source;
	}

}
