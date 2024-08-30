package org.jvnet.jaxb.annox.samples.po.tests;

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.jvnet.jaxb.annox.samples.po.PurchaseOrderType;
import org.jvnet.jaxb.annox.xml.bind.AnnoxAnnotationReader;

import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.v2.model.annotation.RuntimeAnnotationReader;

public class AnnotationReaderTest {

    @Test
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

		Assertions.assertNotNull(purchaseOrder.getOrderDate());
		Assertions.assertNotNull(purchaseOrder.getShipTo());
		Assertions.assertNotNull(purchaseOrder.getBillTo());
		Assertions.assertEquals("Hurry, my lawn is going wild!", purchaseOrder.getComment());
		Assertions.assertNotNull(purchaseOrder.getItems());
		Assertions.assertEquals(2, purchaseOrder.getItems().getItem().size());
		Assertions.assertEquals("Confirm this is electric", purchaseOrder.getItems().getItem().get(0).getComment());

	}

}
