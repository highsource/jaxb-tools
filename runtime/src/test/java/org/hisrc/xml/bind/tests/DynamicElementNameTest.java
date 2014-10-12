package org.hisrc.xml.bind.tests;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

public class DynamicElementNameTest {

	@Test
	public void marshallsDynamicElementName() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class
				.getPackage().getName());
		final Characteristics characteristics = new Characteristics();
		final Characteristic characteristic = new Characteristic();
		characteristic.setCharacteristic("store_capacity");
		characteristic.setValue("40");
		characteristics.getCharacteristics().add(
				new CharacteristicElement(characteristic));
		context.createMarshaller().marshal(characteristics, System.out);
	}
}
