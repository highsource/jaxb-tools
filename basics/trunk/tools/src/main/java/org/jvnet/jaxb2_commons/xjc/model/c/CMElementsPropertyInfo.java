package org.jvnet.jaxb2_commons.xjc.model.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypedElement;

public class CMElementsPropertyInfo extends CMPropertyInfo implements
		MElementsPropertyInfo {

	private final List<MTypedElement> typedElements = new ArrayList<MTypedElement>();
	private final List<MTypedElement> unmodifiableTypedElements = Collections
			.unmodifiableList(typedElements);
	private final QName wrapperElementName;
	private final boolean listed;

	public CMElementsPropertyInfo(String privateName, boolean collection,
			Collection<MTypedElement> typedElements, QName wrapperElementName,
			boolean listed) {
		super(privateName, collection);
		Validate.noNullElements(typedElements);
		Validate.notEmpty(typedElements);
		Validate.isTrue(typedElements.size() > 1);
		this.typedElements.addAll(typedElements);
		this.wrapperElementName = wrapperElementName;
		this.listed = listed;
	}

	public List<MTypedElement> getTypedElements() {
		return unmodifiableTypedElements;
	}

	public QName getWrapperElementName() {
		return wrapperElementName;
	}

	public boolean isListed() {
		return listed;
	}
}
