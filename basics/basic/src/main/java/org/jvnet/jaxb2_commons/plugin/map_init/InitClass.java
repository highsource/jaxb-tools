package org.jvnet.jaxb2_commons.plugin.map_init;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = "urn:jaxb.jvnet.org:plugin:mapinit", name = "class")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class InitClass {

	private String className;

	@XmlValue
	@XmlJavaTypeAdapter(value = CollapsedStringAdapter.class)
	String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
