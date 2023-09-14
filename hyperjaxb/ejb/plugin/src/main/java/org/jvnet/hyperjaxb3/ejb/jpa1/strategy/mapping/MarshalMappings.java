package org.jvnet.hyperjaxb3.ejb.jpa1.strategy.mapping;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa1.JPA1Utils;

import com.sun.java.xml.ns.persistence.orm.EntityMappings;

public class MarshalMappings extends
		org.jvnet.hyperjaxb3.ejb.strategy.mapping.MarshalMappings {
	
	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA1Utils.createMarshaller();
	}

	@Override
	protected EntityMappings createEntityMappings() {
		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("1.0");
		return entityMappings;
	}

}
