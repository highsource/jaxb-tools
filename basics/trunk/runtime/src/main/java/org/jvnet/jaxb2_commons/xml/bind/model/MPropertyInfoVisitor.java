package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MPropertyInfoVisitor<V> {

	public V visitElementPropertyInfo(MElementPropertyInfo info);

	public V visitElementsPropertyInfo(MElementsPropertyInfo info);

	public V visitAnyElementPropertyInfo(MAnyElementPropertyInfo info);

	public V visitAttributePropertyInfo(MAttributePropertyInfo info);

	public V visitAnyAttributePropertyInfo(MAnyAttributePropertyInfo info);

	public V visitValuePropertyInfo(MValuePropertyInfo info);

	public V visitElementRefPropertyInfo(MElementRefPropertyInfo info);

	public V visitElementRefsPropertyInfo(MElementRefsPropertyInfo info);

}
