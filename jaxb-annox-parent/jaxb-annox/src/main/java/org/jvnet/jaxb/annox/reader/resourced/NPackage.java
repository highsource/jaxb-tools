package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "package")
@XmlType(name = "packageType")
@XmlSeeAlso( { NClass.class })
public class NPackage {
	@XmlAttribute
	public String name;
	@XmlAnyElement(lax = true)
	public List<Object> content;

}
