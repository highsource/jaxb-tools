package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "class")
@XmlType(name = "classType")
@XmlSeeAlso( { NField.class, NConstructor.class, NMethod.class })
public class NClass {
	@XmlAttribute
	public String name;
	@XmlAnyElement(lax = true)
	public List<Object> content;

}
