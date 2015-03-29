package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMAnyElementPropertyInfo<T, C extends T> extends CMPropertyInfo<T, C>
		implements MAnyElementPropertyInfo<T, C> {

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMAnyElementPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			boolean mixed, boolean domAllowed, boolean typedObjectAllowed) {
		super(origin, classInfo, privateName, collection);
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	public boolean isMixed() {
		return mixed;
	}

	public boolean isDomAllowed() {
		return domAllowed;
	}

	public boolean isTypedObjectAllowed() {
		return typedObjectAllowed;
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitAnyElementPropertyInfo(this);
	}

}
