package org.jvnet.jaxb.plugin.map_init;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "urn:jaxb.jvnet.org:plugin:mapinit", name = "objectFactory")
@XmlType(propOrder = { "initClass" })
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ObjectFactoryCustomization {

    private InitClass initClass;

    @XmlElement(namespace = "urn:jaxb.jvnet.org:plugin:mapinit", name = "class")
    public InitClass getInitClass() {
        return initClass;
    }

    public void setInitClass(InitClass initClass) {
        this.initClass = initClass;
    }

}
