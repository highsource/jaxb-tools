package org.jvnet.hyperjaxb3.ejb.schemas.customizations.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.SingleProperty;

public class UnmarshalPersistenceTest {

	protected JAXBContext getContext() {
		return Customizations.getContext();
	}

	protected Persistence unmarshal(String resourceName) throws IOException,
			JAXBException {
		Objects.requireNonNull(resourceName);
		try (final InputStream is = resourceName.startsWith("/")
                ? getClass().getClassLoader().getResourceAsStream(resourceName.substring(1))
                : getClass().getResourceAsStream(resourceName)) {
            Assertions.assertNotNull(is);
			@SuppressWarnings("unchecked")
			final JAXBElement<Persistence> persistenceElement = (JAXBElement<Persistence>) getContext()
					.createUnmarshaller().unmarshal(is);
			return persistenceElement.getValue();
		}
	}

    @Test
	public void testPersistence0() throws Exception {
		final Persistence persistence = unmarshal("persistence[0].xml");

		final List<SingleProperty> defaultSingleProperties = persistence
				.getDefaultSingleProperty();

		Assertions.assertFalse(defaultSingleProperties.isEmpty());

		final SingleProperty singleProperty = defaultSingleProperties.get(0);

        Assertions.assertEquals(255, singleProperty.getBasic().getColumn().getLength()
				.intValue());
	}

}
