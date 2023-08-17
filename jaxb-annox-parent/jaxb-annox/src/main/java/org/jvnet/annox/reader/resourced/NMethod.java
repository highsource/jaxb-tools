package org.jvnet.annox.reader.resourced;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

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
