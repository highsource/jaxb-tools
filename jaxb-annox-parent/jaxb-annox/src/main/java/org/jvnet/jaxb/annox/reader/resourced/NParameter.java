package org.jvnet.jaxb.annox.reader.resourced;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlRootElement(name = "parameter")
@XmlType(name = "parameterType")
public class NParameter {
	@XmlAttribute
	public int index;
	@XmlAnyElement
	public List<Element> content;

}
