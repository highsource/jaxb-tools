package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

import com.sun.xml.bind.v2.model.core.PropertyInfo;

public class CMPropertyInfoOrigin<T, C, PI extends PropertyInfo<T, C>> implements MPropertyInfoOrigin,
		PropertyInfoOrigin<T, C, PI> {

	private final PI source;

	public CMPropertyInfoOrigin(PI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public PI getSource() {
		return source;
	}

}
