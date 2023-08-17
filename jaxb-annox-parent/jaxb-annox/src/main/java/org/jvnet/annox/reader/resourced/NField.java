package org.jvnet.annox.reader.resourced;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlRootElement(name = "field")
@XmlType(name = "fieldType")
public class NField {
	@XmlAttribute
	public String name;
	@XmlAnyElement
	public List<Element> content;

}
