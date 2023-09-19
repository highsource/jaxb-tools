package org.jvnet.hyperjaxb3.persistence.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import jakarta.xml.ns.persistence.Persistence;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

public class PersistenceUtils {

	private PersistenceUtils() {
	}

	/**
	 * JAXB context to process Hibernate configuration.
	 */
	public static final JAXBContext CONTEXT;
	static {
		try {
			CONTEXT = JAXBContext.newInstance(
					PersistenceConstants.CONTEXT_PATH, Persistence.class
							.getClassLoader());
		} catch (JAXBException jaxbex) {

			throw new ExceptionInInitializerError(jaxbex);
		}
	}

	public static final NamespacePrefixMapper NAMESPACE_PREFIX_MAPPER = new NamespacePrefixMapper() {
		public String getPreferredPrefix(String namespaceUri,
				String suggestion, boolean requirePrefix) {
			if (PersistenceConstants.ORM_NAMESPACE_URI.equals(namespaceUri)) {
				return "orm";
			} else if (PersistenceConstants.PERSISTENCE_NAMESPACE_URI
					.equals(namespaceUri)) {
				return "";
			} else {
				return suggestion;
			}
		}
	};
}
