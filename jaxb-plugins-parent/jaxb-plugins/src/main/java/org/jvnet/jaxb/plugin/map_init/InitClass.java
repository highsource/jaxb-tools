package org.jvnet.jaxb.plugin.map_init;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
