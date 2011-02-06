package org.jvnet.jaxb2_commons.xml.bind.model;

public interface MTypeInfo {

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor);
}
