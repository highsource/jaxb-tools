package org.jvnet.hyperjaxb3.persistence.jpa2;

import org.jvnet.hyperjaxb3.persistence.util.PersistenceConstants;

public class JPA2Constants {

	private JPA2Constants() {
	}

	public static final String SCHEMA_LOCATION = PersistenceConstants.PERSISTENCE_NAMESPACE_URI
			+ " http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd\n"
			+ PersistenceConstants.ORM_NAMESPACE_URI
			+ " http://java.sun.com/xml/ns/persistence/orm_2_0.xsd";

}
