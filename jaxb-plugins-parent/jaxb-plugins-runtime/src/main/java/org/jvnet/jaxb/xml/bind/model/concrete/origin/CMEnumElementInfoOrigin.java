package org.jvnet.jaxb.xml.bind.model.concrete.origin;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.origin.MElementInfoOrigin;
import org.glassfish.jaxb.core.v2.model.core.EnumLeafInfo;

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
