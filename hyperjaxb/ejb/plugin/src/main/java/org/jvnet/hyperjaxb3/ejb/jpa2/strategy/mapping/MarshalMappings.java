package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.mapping;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa2.JPA2Utils;

import com.sun.java.xml.ns.persistence.orm.EntityMappings;

public class MarshalMappings extends
		org.jvnet.hyperjaxb3.ejb.strategy.mapping.MarshalMappings {

	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA2Utils.createMarshaller();
	}

	@Override
	protected EntityMappings createEntityMappings() {
		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("2.0");
		return entityMappings;
	}

}
