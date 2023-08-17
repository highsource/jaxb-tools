package org.jvnet.annox.reader.resourced;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "class")
@XmlType(name = "classType")
@XmlSeeAlso( { NField.class, NConstructor.class, NMethod.class })
public class NClass {
	@XmlAttribute
	public String name;
	@XmlAnyElement(lax = true)
	public List<Object> content;

}
