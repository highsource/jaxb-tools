package org.jvnet.jaxb.plugin.inheritance;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "extends")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LegacyExtendsClass {

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
