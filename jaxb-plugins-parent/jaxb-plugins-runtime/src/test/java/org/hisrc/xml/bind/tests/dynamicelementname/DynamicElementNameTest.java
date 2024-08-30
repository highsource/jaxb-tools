package org.hisrc.xml.bind.tests.dynamicelementname;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

public class DynamicElementNameTest {

	@Test
	public void marshallsDynamicElementName() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		final Characteristics characteristics = new Characteristics();
		final Characteristic characteristic = new Characteristic(
				"store_capacity", "40");
		characteristics.getCharacteristics().add(characteristic);
		context.createMarshaller().marshal(characteristics, System.out);
	}

	@XmlRegistry
	public static class ObjectFactory {

		public Characteristics createCharacteristics() {
			return new Characteristics();
		}

		@XmlElementDecl(name = "characteristic")
		public JAXBElement<String> createCharacteristic(String value) {
			return new Characteristic(value);
		}

	}

	@XmlRootElement(name = "characteristics")
	public static class Characteristics {

		private final List<Characteristic> characteristics = new LinkedList<Characteristic>();

		@XmlElementRef(name = "characteristic")
		public List<Characteristic> getCharacteristics() {
			return characteristics;
		}

	}

	public static class Characteristic extends JAXBElement<String> {

		private static final long serialVersionUID = 1L;
		public static final QName NAME = new QName("characteristic");

		public Characteristic(String value) {
			super(NAME, String.class, value);
		}

		public Characteristic(String characteristic, String value) {
			super(NAME, String.class, value);
			this.characteristic = characteristic;
		}

		@Override
		public QName getName() {
			final String characteristic = getCharacteristic();
			if (characteristic != null) {
				return new QName(characteristic);
			}
			return super.getName();
		}

		private String characteristic;

		@XmlTransient
		public String getCharacteristic() {
			return characteristic;
		}

		public void setCharacteristic(String characteristic) {
			this.characteristic = characteristic;
		}
	}
}
