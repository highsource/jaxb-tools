package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.processor;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa2.JPA2Utils;

public class PersistenceMarshaller extends
		org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller {

	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA2Utils.createMarshaller();
	}

}
