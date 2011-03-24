package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMElementRefsPropertyInfo<T, C> extends CMPropertyInfo<T, C>
		implements MElementRefsPropertyInfo<T, C> {

	private final QName wrapperElementName;

	private final List<MElementTypeInfo<T, C>> elementTypeInfos = new ArrayList<MElementTypeInfo<T, C>>();
	private final List<MElementTypeInfo<T, C>> unmodifiableElementTypeInfos = Collections
			.unmodifiableList(elementTypeInfos);

	private final boolean mixed;
	private final boolean domAllowed;
	private final boolean typedObjectAllowed;

	public CMElementRefsPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			Collection<MElementTypeInfo<T, C>> elementTypeInfos,
			QName wrapperElementName, boolean mixed, boolean domAllowed,
			boolean typedObjectAllowed) {
		super(origin, classInfo, privateName, collection);
		Validate.noNullElements(elementTypeInfos);
		Validate.notEmpty(elementTypeInfos);
		Validate.isTrue(elementTypeInfos.size() > 1);
		this.elementTypeInfos.addAll(elementTypeInfos);
		this.wrapperElementName = wrapperElementName;
		this.mixed = mixed;
		this.domAllowed = domAllowed;
		this.typedObjectAllowed = typedObjectAllowed;
	}

	public List<MElementTypeInfo<T, C>> getElementTypeInfos() {
		return unmodifiableElementTypeInfos;
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

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitElementRefsPropertyInfo(this);
	}

}
