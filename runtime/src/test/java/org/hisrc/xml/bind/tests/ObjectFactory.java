package org.hisrc.xml.bind.tests;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	public Characteristic createCharacteristic() {
		return new Characteristic();
	}

	public Characteristics createCharacteristics() {
		return new Characteristics();
	}

	@XmlElementDecl(namespace = "", name = "characteristic")
	public JAXBElement<Characteristic> createCharacteristic(
			Characteristic characteristic) {
		return new CharacteristicElement(characteristic);
	}

	@XmlElementDecl(namespace = "", name = "charactertistics")
	public JAXBElement<Characteristics> createCharacteristics(
			Characteristics value) {
		return new JAXBElement<Characteristics>(new QName("charactertistics"),
				Characteristics.class, value);
	}

}
