package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;

public class CMAnyElementPropertyInfo extends CMPropertyInfo implements
		MAnyElementPropertyInfo {

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMAnyElementPropertyInfo(String privateName, boolean collection,
			boolean mixed, boolean domAllowed, boolean typedObjectAllowed) {
		super(privateName, collection);
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	@Override
	public boolean isMixed() {
		return mixed;
	}

	@Override
	public boolean isDomAllowed() {
		return domAllowed;
	}

	@Override
	public boolean isTypedObjectAllowed() {
		return typedObjectAllowed;
	}

	@Override
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitAnyElementPropertyInfo(this);
	}

}
