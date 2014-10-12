package org.hisrc.xml.bind.tests;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "characteristics")
public class Characteristics {

	private final List<JAXBElement<Characteristic>> characteristics = new LinkedList<JAXBElement<Characteristic>>();

	public Characteristic createCharacteristic() {
		return new Characteristic();
	}

	@XmlElementRef(name = "characteristic", type = JAXBElement.class)
	public List<JAXBElement<Characteristic>> getCharacteristics() {
		return characteristics;
	}

}