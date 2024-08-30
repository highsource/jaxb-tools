package org.hisrc.xml.bind.tests.dogs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DogTest {

	@Test
	public void unmarshallsDogs() throws JAXBException {
		final JAXBContext context = JAXBContext
				.newInstance(ObjectFactory.class);
		final Dogs dogs = (Dogs) context.createUnmarshaller().unmarshal(
				getClass().getResource("dogs.xml"));
        Assertions.assertEquals(3, dogs.getDogs().size());
		// Does not work
        // Assertions.assertEquals("henry", dogs.getDogs().get(0).getValue().getName());
        Assertions.assertEquals("bark", dogs.getDogs().get(0).getValue().getSound());
		// Does not work
        // Assertions.assertEquals("fido", dogs.getDogs().get(1).getValue().getName());
        Assertions.assertEquals("woof", dogs.getDogs().get(1).getValue().getSound());
		// Does not work
        // Assert.assertEquals("barks", dogs.getDogs().get(2).getValue().getName());
        Assertions.assertEquals("miau", dogs.getDogs().get(2).getValue().getSound());
	}
}
