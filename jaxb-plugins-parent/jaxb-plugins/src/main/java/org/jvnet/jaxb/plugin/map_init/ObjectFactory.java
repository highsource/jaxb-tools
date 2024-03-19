package org.jvnet.jaxb.plugin.map_init;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public InitClass createInitClass() {
        return new InitClass();
    }

    public ObjectFactoryCustomization createObjectFactoryCustomization() {
        return new ObjectFactoryCustomization();
    }

}
