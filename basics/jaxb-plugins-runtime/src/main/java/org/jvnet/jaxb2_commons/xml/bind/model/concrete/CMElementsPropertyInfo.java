package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMElementsPropertyInfo<T, C extends T> extends
		CMPropertyInfo<T, C> implements MElementsPropertyInfo<T, C> {

	private final List<MElementTypeRef<T, C>> elementTypeInfos = new ArrayList<MElementTypeRef<T, C>>();
	private final List<MElementTypeRef<T, C>> unmodifiableElementTypeInfos = Collections
			.unmodifiableList(elementTypeInfos);
	private final QName wrapperElementName;

	public CMElementsPropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			boolean required,
			Collection<MElementTypeRef<T, C>> elementTypeInfos,
			QName wrapperElementName) {
		super(origin, classInfo, privateName, collection, required);
		Validate.noNullElements(elementTypeInfos);
		Validate.notEmpty(elementTypeInfos);
		Validate.isTrue(elementTypeInfos.size() > 1);
		this.elementTypeInfos.addAll(elementTypeInfos);
		this.wrapperElementName = wrapperElementName;
	}

	public List<MElementTypeRef<T, C>> getElementTypeInfos() {
		return unmodifiableElementTypeInfos;
	}

	public QName getWrapperElementName() {
		return wrapperElementName;
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitElementsPropertyInfo(this);
	}

}
