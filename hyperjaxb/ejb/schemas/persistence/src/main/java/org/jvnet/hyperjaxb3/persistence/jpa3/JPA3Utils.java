package org.jvnet.hyperjaxb3.persistence.jpa3;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;

public class JPA3Utils {

	public static Marshaller createMarshaller() throws JAXBException {
		final Marshaller marshaller = PersistenceUtils.CONTEXT
				.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
				JPA3Constants.SCHEMA_LOCATION);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper",
				PersistenceUtils.NAMESPACE_PREFIX_MAPPER);

		return marshaller;
	}

}
