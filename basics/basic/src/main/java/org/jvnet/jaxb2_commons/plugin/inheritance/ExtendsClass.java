package org.jvnet.jaxb2_commons.plugin.inheritance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "extends")
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
