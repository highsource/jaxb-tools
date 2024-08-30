package org.jvnet.hyperjaxb3.ejb.schemas.customizations.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
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
		Validate.notNull(resourceName);
		final InputStream is;
		if (resourceName.startsWith("/")) {
			is = getClass().getClassLoader().getResourceAsStream(
					resourceName.substring(1));
		} else {
			is = getClass().getResourceAsStream(resourceName);
		}
        Assertions.assertNotNull(is);
		try {
			@SuppressWarnings("unchecked")
			final JAXBElement<Persistence> persistenceElement = (JAXBElement<Persistence>) getContext()
					.createUnmarshaller().unmarshal(is);
			return persistenceElement.getValue();
		} finally {
			IOUtils.closeQuietly(is);
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
