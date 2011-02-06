package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;

public class CMElementRefsPropertyInfo extends CMPropertyInfo implements
		MElementRefsPropertyInfo {

	private final QName wrapperElementName;

	private final List<MElementTypeInfo> elementTypeInfos = new ArrayList<MElementTypeInfo>();
	private final List<MElementTypeInfo> unmodifiableElementTypeInfos = Collections
			.unmodifiableList(elementTypeInfos);

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMElementRefsPropertyInfo(String privateName, boolean collection,
			Collection<MElementTypeInfo> elementTypeInfos, QName wrapperElementName,
			boolean mixed, boolean domAllowed, boolean typedObjectAllowed) {
		super(privateName, collection);
		Validate.noNullElements(elementTypeInfos);
		Validate.notEmpty(elementTypeInfos);
		Validate.isTrue(elementTypeInfos.size() > 1);
		this.elementTypeInfos.addAll(elementTypeInfos);
		this.wrapperElementName = wrapperElementName;
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	@Override
	public List<MElementTypeInfo> getElementTypeInfos() {
		return unmodifiableElementTypeInfos;
	}

	@Override
	public QName getWrapperElementName() {
		return wrapperElementName;
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
		return visitor.visitElementRefsPropertyInfo(this);
	}

}
