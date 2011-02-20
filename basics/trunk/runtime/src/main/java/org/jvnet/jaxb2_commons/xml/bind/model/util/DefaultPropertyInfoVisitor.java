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

public class DefaultPropertyInfoVisitor<V> implements MPropertyInfoVisitor<V> {

	public V visitPropertyInfo(MPropertyInfo info) {
		return null;
	}

	@Override
	public V visitElementPropertyInfo(MElementPropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitElementsPropertyInfo(MElementsPropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitAnyElementPropertyInfo(MAnyElementPropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitAttributePropertyInfo(MAttributePropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitAnyAttributePropertyInfo(MAnyAttributePropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitValuePropertyInfo(MValuePropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitElementRefPropertyInfo(MElementRefPropertyInfo info) {
		return visitPropertyInfo(info);
	}

	@Override
	public V visitElementRefsPropertyInfo(MElementRefsPropertyInfo info) {
		return visitPropertyInfo(info);
	}

}
