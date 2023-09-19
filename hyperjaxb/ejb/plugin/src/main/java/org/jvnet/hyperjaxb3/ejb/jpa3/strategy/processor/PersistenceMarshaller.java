package org.jvnet.hyperjaxb3.ejb.jpa3.strategy.processor;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa3.JPA3Utils;

public class PersistenceMarshaller extends
		org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller {

	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA3Utils.createMarshaller();
	}

}
