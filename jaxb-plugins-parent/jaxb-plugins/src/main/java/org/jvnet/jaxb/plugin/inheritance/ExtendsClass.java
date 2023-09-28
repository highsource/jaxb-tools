package org.jvnet.jaxb.plugin.inheritance;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = "urn:jaxb.jvnet.org:plugin:inheritance", name = "extends")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExtendsClass {

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
