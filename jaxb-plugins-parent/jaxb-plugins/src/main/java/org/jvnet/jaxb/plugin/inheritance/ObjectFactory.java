package org.jvnet.jaxb.plugin.inheritance;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    // TODO: [#403] jt-403 Remove support for deprecated legacy jaxb2-commons xml namespace  
	public LegacyExtendsClass createLegacyExtendsClass() {
		return new LegacyExtendsClass();
	}

    // TODO: [#403] jt-403 Remove support for deprecated legacy jaxb2-commons xml namespace  
	public LegacyImplementsInterface createLegacyImplementsInterface() {
		return new LegacyImplementsInterface();
	}

    // TODO: [#403] jt-403 Remove support for deprecated legacy jaxb2-commons xml namespace  
	public LegacyObjectFactoryCustomization createLegacyObjectFactoryCustomization() {
		return new LegacyObjectFactoryCustomization();
	}

}
