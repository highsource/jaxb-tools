package org.jvnet.hyperjaxb3.sb.tests;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Attribute")
public class AttributeDefinition<T> {

	T value;

	@XmlElement(name = "Value")
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
}