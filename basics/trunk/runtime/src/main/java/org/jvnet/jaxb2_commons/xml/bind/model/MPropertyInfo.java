package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MPropertyInfo {

	public String getPrivateName();

	public boolean isCollection();

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor);
}
