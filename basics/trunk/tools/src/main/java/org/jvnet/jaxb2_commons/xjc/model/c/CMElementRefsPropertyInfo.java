package org.jvnet.jaxb2_commons.xjc.model.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypedElement;

public class CMElementRefsPropertyInfo extends CMPropertyInfo implements
		MElementRefsPropertyInfo {

	private final QName wrapperElementName;

	private final List<MTypedElement> typedElements = new ArrayList<MTypedElement>();
	private final List<MTypedElement> unmodifiableTypedElements = Collections
			.unmodifiableList(typedElements);

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMElementRefsPropertyInfo(String privateName, boolean collection,
			Collection<MTypedElement> typedElements, QName wrapperElementName,
			boolean mixed, boolean domAllowed, boolean typedObjectAllowed) {
		super(privateName, collection);
		Validate.noNullElements(typedElements);
		Validate.notEmpty(typedElements);
		Validate.isTrue(typedElements.size() > 1);
		this.typedElements.addAll(typedElements);
		this.wrapperElementName = wrapperElementName;
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	public List<MTypedElement> getTypedElements() {
		return unmodifiableTypedElements;
	}

	public QName getWrapperElementName() {
		return wrapperElementName;
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

}
