package org.jvnet.jaxb.xml.bind.model.concrete.origin;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.origin.MEnumConstantInfoOrigin;
import org.glassfish.jaxb.core.v2.model.core.EnumConstant;

public class CMEnumConstantInfoOrigin<T, C, ECI extends EnumConstant<T, C>>
		implements MEnumConstantInfoOrigin, EnumConstantOrigin<T, C, ECI> {

	private final ECI source;

	public CMEnumConstantInfoOrigin(ECI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public ECI getSource() {
		return source;
	}

}
