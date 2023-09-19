package org.jvnet.hyperjaxb3.ejb.jpa3.strategy.mapping;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa3.JPA3Utils;

import jakarta.xml.ns.persistence.orm.EntityMappings;

public class MarshalMappings extends
		org.jvnet.hyperjaxb3.ejb.strategy.mapping.MarshalMappings {

	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA3Utils.createMarshaller();
	}

	@Override
	protected EntityMappings createEntityMappings() {
		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("3.0");
		return entityMappings;
	}

}
