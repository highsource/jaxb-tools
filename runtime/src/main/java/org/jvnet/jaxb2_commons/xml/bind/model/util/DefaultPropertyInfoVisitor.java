package org.jvnet.jaxb2_commons.xml.bind.model.util;

import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MAnyElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MValuePropertyInfo;

public class DefaultPropertyInfoVisitor<T, C, V> implements
		MPropertyInfoVisitor<T, C, V> {

	public V visitPropertyInfo(MPropertyInfo<T, C> info) {
		return null;
	}

	public V visitElementPropertyInfo(MElementPropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitElementsPropertyInfo(MElementsPropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitAnyElementPropertyInfo(MAnyElementPropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitAttributePropertyInfo(MAttributePropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitAnyAttributePropertyInfo(MAnyAttributePropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitValuePropertyInfo(MValuePropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitElementRefPropertyInfo(MElementRefPropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

	public V visitElementRefsPropertyInfo(MElementRefsPropertyInfo<T, C> info) {
		return visitPropertyInfo(info);
	}

}
