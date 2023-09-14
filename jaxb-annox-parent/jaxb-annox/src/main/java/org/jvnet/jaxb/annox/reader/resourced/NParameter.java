package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlRootElement(name = "parameter")
@XmlType(name = "parameterType")
public class NParameter {
	@XmlAttribute
	public int index;
	@XmlAnyElement
	public List<Element> content;

}
