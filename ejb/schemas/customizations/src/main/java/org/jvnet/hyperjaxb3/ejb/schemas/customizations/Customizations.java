package org.jvnet.hyperjaxb3.ejb.schemas.customizations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;

import org.jvnet.jaxb2_commons.lang.ContextUtils;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizable;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;

public class Customizations {

	public static final String NAMESPACE_URI = "http://hyperjaxb3.jvnet.org/ejb/schemas/customizations";

	public static final String ORM_NAMESPACE_URI = "http://java.sun.com/xml/ns/persistence/orm";

	public static final Set<String> NAMESPACES;
	static {
		final Set<String> namespaces = new HashSet<String>(3);
		namespaces.add(NAMESPACE_URI);
		namespaces.add("http://java.sun.com/xml/ns/persistence");
		namespaces.add(ORM_NAMESPACE_URI);
		NAMESPACES = Collections.unmodifiableSet(namespaces);
	}

	public static final String CONTEXT_PATH = ContextUtils.getContextPath(
			com.sun.java.xml.ns.persistence.ObjectFactory.class,
			com.sun.java.xml.ns.persistence.orm.ObjectFactory.class,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ObjectFactory.class

	);

	private static final JAXBContext context;
	static {
		try {
			context = JAXBContext
					.newInstance(
							CONTEXT_PATH,
							org.jvnet.hyperjaxb3.ejb.schemas.customizations.ObjectFactory.class
									.getClassLoader());
		} catch (JAXBException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static JAXBContext getContext() {
		return context;
	}

	public static QName hj(String localPart) {
		return new QName(NAMESPACE_URI, localPart);
	}
	
	public static QName orm(String localPart) {
		return new QName(ORM_NAMESPACE_URI, localPart);
	}

	public static final QName PERSISTENCE_ELEMENT_NAME = hj("persistence");

	public static final QName ITEM_ELEMENT_NAME = hj("item");

	public static final QName IGNORED_ELEMENT_NAME = hj("ignored");

	public static final QName IGNORED_PACKAGE_ELEMENT_NAME = hj("ignored-package");

	public static final QName GENERATED_CLASS_ELEMENT_NAME = hj("generated-class");

	public static final QName GENERATED_PROPERTY_ELEMENT_NAME = hj("generated-property");

	public static final QName GENERATED_ID_ELEMENT_NAME = hj("generated-id");

	public static final QName GENERATED_VERSION_ELEMENT_NAME = hj("generated-version");

	public static final QName EMBEDDED_ID_ELEMENT_NAME = hj("embedded-id");

	public static final QName ID_ELEMENT_NAME = hj("id");

	public static final QName VERSION_ELEMENT_NAME = hj("version");

	public static final QName TABLE_ELEMENT_NAME = hj("table");

	public static final QName COLUMN_ELEMENT_NAME = hj("column");

	public static final QName ONE_TO_MANY_ELEMENT_NAME = hj("one-to-many");

	public static final QName ONE_TO_ONE_ELEMENT_NAME = hj("one-to-one");

	public static final QName MANY_TO_ONE_ELEMENT_NAME = hj("many-to-one");

	public static final QName MANY_TO_MANY_ELEMENT_NAME = hj("many-to-many");

	public static final QName ELEMENT_COLLECTION_ELEMENT_NAME = hj("element-collection");

	public static final QName BASIC_ELEMENT_NAME = hj("basic");

	public static final QName EMBEDDED_ELEMENT_NAME = hj("embedded");

	public static QName GENERATED_ELEMENT_NAME = new QName(
			"http://jaxb2-commons.dev.java.net/basic", "generated");
	
	
	
	// 
	public static final QName ENTITY_ELEMENT_NAME = hj("entity");

	public static final QName MAPPED_SUPERCLASS_ELEMENT_NAME = hj("mapped-superclass");

	public static final QName EMBEDDABLE_ELEMENT_NAME = hj("embeddable");
	
	public static final QName JAXB_CONTEXT_ELEMENT_NAME = hj("jaxb-context");


	public static void markAsAcknowledged(
			final CPluginCustomization customization) {

		if (customization != null
				&& NAMESPACE_URI
						.equals(customization.element.getNamespaceURI())) {
			customization.markAsAcknowledged();

		}
	}

	public static void markAsAcknowledged(final CCustomizations customizations) {

		for (CPluginCustomization customization : customizations) {
			markAsAcknowledged(customization);
		}
	}

	public static void markAsAcknowledged(final CPropertyInfo customizable) {

		markAsAcknowledged(CustomizationUtils.getCustomizations(customizable));
	}

	public static void markAsAcknowledged(final CClassInfo customizable) {

		markAsAcknowledged(CustomizationUtils.getCustomizations(customizable));
	}

	public static CPluginCustomization createCustomization(Object object) {
		final Marshaller marshaller;
		try {
			marshaller = getContext().createMarshaller();
		} catch (JAXBException ex) {
			final AssertionError error = new AssertionError(
					"Marshaller could not be created.");
			error.initCause(ex);
			throw error;
		}

		final DOMResult result = new DOMResult();
		try {
			marshaller.marshal(object, result);
		} catch (JAXBException ex) {
			throw new IllegalArgumentException(
					"The argument could not be marshalled.", ex);
		}

		final Node node = result.getNode();
		final Element element;
		if (node instanceof Document) {
			final Document document = (Document) node;
			element = document.getDocumentElement();

		} else if (node instanceof Element) {
			element = (Element) node;
		} else {
			throw new AssertionError(
					"Could not retrive the element from the marshalled result.");
		}
		return CustomizationUtils.createCustomization(element);
	}

	private final static com.sun.java.xml.ns.persistence.ObjectFactory persistenceObjectFactory = new com.sun.java.xml.ns.persistence.ObjectFactory();

	private final static com.sun.java.xml.ns.persistence.orm.ObjectFactory ormObjectFactory = new com.sun.java.xml.ns.persistence.orm.ObjectFactory();
	
	private final static org.jvnet.hyperjaxb3.ejb.schemas.customizations.ObjectFactory customizationsObjectFactory = new org.jvnet.hyperjaxb3.ejb.schemas.customizations.ObjectFactory();

	public static com.sun.java.xml.ns.persistence.ObjectFactory getPersistenceObjectFactory() {
		return persistenceObjectFactory;
	}

	public static com.sun.java.xml.ns.persistence.orm.ObjectFactory getOrmObjectFactory() {
		return ormObjectFactory;
	}

	public static org.jvnet.hyperjaxb3.ejb.schemas.customizations.ObjectFactory getCustomizationsObjectFactory() {
		return customizationsObjectFactory;
	}


	public static CPluginCustomization createCustomization$Ignored() {

		final CPluginCustomization customization = CustomizationUtils
				.createCustomization(Customizations.IGNORED_ELEMENT_NAME);
		customization.markAsAcknowledged();
		return customization;
	}

	public static CPluginCustomization createCustomization$Generated() {

		final CPluginCustomization customization = CustomizationUtils
				.createCustomization(GENERATED_ELEMENT_NAME);
		customization.markAsAcknowledged();
		return customization;
	}

	public static void markIgnored(CCustomizable customizable) {
		customizable.getCustomizations().add(createCustomization$Ignored());
	}

	public static void markGenerated(CCustomizable customizable) {
		customizable.getCustomizations().add(createCustomization$Generated());
	}

	public static boolean isGenerated(CClassInfo classInfo) {
		return CustomizationUtils.containsCustomization(classInfo,
				GENERATED_ELEMENT_NAME);
	}

	public static boolean isGenerated(CPropertyInfo propertyInfo) {
		return CustomizationUtils.containsCustomization(propertyInfo,
				GENERATED_ELEMENT_NAME);
	}

}
