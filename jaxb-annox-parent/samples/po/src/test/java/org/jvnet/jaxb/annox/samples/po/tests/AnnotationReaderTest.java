package org.jvnet.jaxb.annox.samples.po.tests;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import junit.framework.TestCase;

import org.jvnet.jaxb.annox.samples.po.PurchaseOrderType;
import org.jvnet.jaxb.annox.xml.bind.AnnoxAnnotationReader;

import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;

public class AnnotationReaderTest extends TestCase {

	public void testAnnotationReader() throws Exception {

		final RuntimeAnnotationReader annotationReader = new AnnoxAnnotationReader();

		final Map<String, Object> properties = new HashMap<String, Object>();

		properties.put(JAXBRIContext.ANNOTATION_READER, annotationReader);

		final JAXBContext context = JAXBContext.newInstance(
				"org.jvnet.jaxb.annox.samples.po", Thread.currentThread()
						.getContextClassLoader(), properties);

		@SuppressWarnings("unchecked")
		final JAXBElement<PurchaseOrderType> purchaseOrderElement = (JAXBElement<PurchaseOrderType>) context
				.createUnmarshaller().unmarshal(
						getClass().getResource("po.xml"));

		final PurchaseOrderType purchaseOrder = purchaseOrderElement.getValue();

		assertNotNull(purchaseOrder.getOrderDate());
		assertNotNull(purchaseOrder.getShipTo());
		assertNotNull(purchaseOrder.getBillTo());
		assertEquals("Hurry, my lawn is going wild!", purchaseOrder
				.getComment());
		assertNotNull(purchaseOrder.getItems());
		assertEquals(2, purchaseOrder.getItems().getItem().size());
		assertEquals("Confirm this is electric", purchaseOrder.getItems()
				.getItem().get(0).getComment());

	}

}
