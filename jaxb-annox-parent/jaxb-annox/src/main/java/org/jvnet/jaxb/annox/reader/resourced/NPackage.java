package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "package")
@XmlType(name = "packageType")
@XmlSeeAlso( { NClass.class })
public class NPackage {
	@XmlAttribute
	public String name;
	@XmlAnyElement(lax = true)
	public List<Object> content;

}
