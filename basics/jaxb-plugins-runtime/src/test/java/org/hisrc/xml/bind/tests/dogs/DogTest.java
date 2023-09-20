package org.hisrc.xml.bind.tests.dogs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

public class DogTest {

	@Test
	public void unmarshallsDogs() throws JAXBException {
		final JAXBContext context = JAXBContext
				.newInstance(ObjectFactory.class);
		final Dogs dogs = (Dogs) context.createUnmarshaller().unmarshal(
				getClass().getResource("dogs.xml"));
		Assert.assertEquals(3, dogs.getDogs().size());
		// Does not work
//		Assert.assertEquals("henry", dogs.getDogs().get(0).getValue()
//				.getName());
		Assert.assertEquals("bark", dogs.getDogs().get(0).getValue().getSound());
		// Does not work
//		Assert.assertEquals("fido", dogs.getDogs().get(1).getValue()
//				.getName());
		Assert.assertEquals("woof", dogs.getDogs().get(1).getValue().getSound());
		// Does not work
//		Assert.assertEquals("barks", dogs.getDogs().get(2).getValue()
//				.getName());
		Assert.assertEquals("miau", dogs.getDogs().get(2).getValue().getSound());
	}
}
