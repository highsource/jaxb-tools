package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MPropertyInfoVisitor<T, C, V> {

	public V visitElementPropertyInfo(MElementPropertyInfo<T,C> info);

	public V visitElementsPropertyInfo(MElementsPropertyInfo<T,C> info);

	public V visitAnyElementPropertyInfo(MAnyElementPropertyInfo<T,C> info);

	public V visitAttributePropertyInfo(MAttributePropertyInfo<T,C> info);

	public V visitAnyAttributePropertyInfo(MAnyAttributePropertyInfo<T,C> info);

	public V visitValuePropertyInfo(MValuePropertyInfo<T,C> info);

	public V visitElementRefPropertyInfo(MElementRefPropertyInfo<T,C> info);

	public V visitElementRefsPropertyInfo(MElementRefsPropertyInfo<T,C> info);

}
