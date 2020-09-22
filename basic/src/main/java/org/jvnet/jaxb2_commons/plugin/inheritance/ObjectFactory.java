package org.jvnet.jaxb2_commons.plugin.inheritance;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ExtendsClass createExtendsClass() {
		return new ExtendsClass();
	}

	public ImplementsInterface createImplementsInterface() {
		return new ImplementsInterface();
	}
	
	public ObjectFactoryCustomization createObjectFactoryCustomization() {
		return new ObjectFactoryCustomization();
	}

}
