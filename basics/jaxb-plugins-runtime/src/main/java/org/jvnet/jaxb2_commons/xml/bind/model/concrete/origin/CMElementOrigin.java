package org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementOrigin;

import org.glassfish.jaxb.core.v2.model.core.Element;

public class CMElementOrigin<T, C, E extends Element<T, C>>
		implements MElementOrigin, ElementOrigin<T, C, E> {

	private final E source;

	public CMElementOrigin(E source) {
		Validate.notNull(source);
		this.source = source;
	}

	public E getSource() {
		return source;
	}

}
