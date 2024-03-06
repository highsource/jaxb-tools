package org.jvnet.jaxb2_commons.plugin.map_init;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
