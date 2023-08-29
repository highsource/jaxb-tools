package org.jvnet.hyperjaxb3.ejb.tutorials.po.stepone;

import generated.ObjectFactory;
import generated.PurchaseOrderType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;

import junit.framework.TestCase;
import org.junit.Assert;
import org.jvnet.jaxb2_commons.lang.ExtendedJAXBEqualsStrategy;

public class JAXBAndJPATest extends TestCase {

	private ObjectFactory objectFactory;

	private EntityManagerFactory entityManagerFactory;

	private JAXBContext context;

	public void setUp() throws Exception {

		objectFactory = new ObjectFactory();

		final Properties persistenceProperties = new Properties();
		InputStream is = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(
					"persistence.properties");
			persistenceProperties.load(is);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {

				}
			}
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(
				"generated", persistenceProperties);

		context = JAXBContext.newInstance("generated");
	}

	@SuppressWarnings("unchecked")
	public void testRoundtrip() throws JAXBException {

		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final Object object = unmarshaller.unmarshal(new File(
				"src/test/samples/po.xml"));
		final PurchaseOrderType alpha = ((JAXBElement<PurchaseOrderType>) object)
				.getValue();

		final EntityManager saveManager = entityManagerFactory
				.createEntityManager();
		saveManager.getTransaction().begin();
		saveManager.persist(alpha);
		saveManager.getTransaction().commit();
		saveManager.close();

		final Long id = alpha.getHjid();

		final EntityManager loadManager = entityManagerFactory
				.createEntityManager();
		final PurchaseOrderType beta = loadManager.find(
				PurchaseOrderType.class, id);
		// Using not default equals strategy since BigDecimal has errors in equals strict equality
		Assert.assertTrue("Objects are not equal.", alpha.equals(null, null, beta, ExtendedJAXBEqualsStrategy.INSTANCE2));
		
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(objectFactory.createPurchaseOrder(beta), System.out);
		loadManager.close();
	}
}
