package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;

public class CMElementsPropertyInfo extends CMPropertyInfo implements
		MElementsPropertyInfo {

	private final List<MElementTypeInfo> elementTypeInfos = new ArrayList<MElementTypeInfo>();
	private final List<MElementTypeInfo> unmodifiableElementTypeInfos = Collections
			.unmodifiableList(elementTypeInfos);
	private final QName wrapperElementName;

	public CMElementsPropertyInfo(String privateName, boolean collection,
			Collection<MElementTypeInfo> elementTypeInfos, QName wrapperElementName) {
		super(privateName, collection);
		Validate.noNullElements(elementTypeInfos);
		Validate.notEmpty(elementTypeInfos);
		Validate.isTrue(elementTypeInfos.size() > 1);
		this.elementTypeInfos.addAll(elementTypeInfos);
		this.wrapperElementName = wrapperElementName;
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
	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor) {
		return visitor.visitElementsPropertyInfo(this);
	}

}
