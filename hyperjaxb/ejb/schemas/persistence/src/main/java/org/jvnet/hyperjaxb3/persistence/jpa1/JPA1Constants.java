package org.jvnet.hyperjaxb3.persistence.jpa1;

import org.jvnet.hyperjaxb3.persistence.util.PersistenceConstants;

public class JPA1Constants {

	private JPA1Constants() {
	}

	public static final String SCHEMA_LOCATION = PersistenceConstants.PERSISTENCE_NAMESPACE_URI
			+ " http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\n"
			+ PersistenceConstants.ORM_NAMESPACE_URI
			+ " http://java.sun.com/xml/ns/persistence/orm_1_0.xsd";

}
