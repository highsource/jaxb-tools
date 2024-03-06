package org.jvnet.jaxb2_commons.plugin.map_init;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public InitClass createInitClass() {
        return new InitClass();
    }

    public ObjectFactoryCustomization createObjectFactoryCustomization() {
        return new ObjectFactoryCustomization();
    }

}
