package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.NamespaceContext;

public interface MDefaultValue {

	public NamespaceContext getDefaultValueNamespaceContext();

	public String getDefaultValue();
}
