package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "method")
@XmlType(name = "methodType")
@XmlSeeAlso( { NParameter.class })
public class NMethod {
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String arguments;
	@XmlAnyElement(lax = true)
	public List<Object> content;

}
