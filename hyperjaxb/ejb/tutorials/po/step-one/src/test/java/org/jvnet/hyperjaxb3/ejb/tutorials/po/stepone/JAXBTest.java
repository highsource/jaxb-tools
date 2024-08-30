package org.jvnet.hyperjaxb3.ejb.tutorials.po.stepone;

import generated.ObjectFactory;
import generated.PurchaseOrderType;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.xml.XMLConstants;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

public class JAXBTest {

	private JAXBContext context;

	private ObjectFactory objectFactory;

    @BeforeEach
	protected void setUp() throws Exception {
		context = JAXBContext.newInstance("generated");
		objectFactory = new ObjectFactory();
	}

    @Test
	public void testUnmarshall() throws JAXBException {
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final Object object = unmarshaller.unmarshal(new File(
				"src/test/samples/po.xml"));
		@SuppressWarnings("unchecked")
		final PurchaseOrderType purchaseOrder = ((JAXBElement<PurchaseOrderType>) object).getValue();
		Assertions.assertEquals("Mill Valley", purchaseOrder.getShipTo().getCity(), "Wrong city");
	}

    @Test
	public void testMarshal() throws JAXBException, XPathException {
		final PurchaseOrderType purchaseOrder = objectFactory
				.createPurchaseOrderType();
		purchaseOrder.setShipTo(objectFactory.createUSAddress());
		purchaseOrder.getShipTo().setCity("New Orleans");
		final JAXBElement<PurchaseOrderType> purchaseOrderElement = objectFactory
				.createPurchaseOrder(purchaseOrder);

		final Marshaller marshaller = context.createMarshaller();

		final DOMResult result = new DOMResult();
		marshaller.marshal(purchaseOrderElement, result);

		final XPathFactory xPathFactory = XPathFactory.newInstance();

		Assertions.assertEquals("New Orleans", xPathFactory.newXPath()
				.evaluate("/purchaseOrder/shipTo/city", result.getNode()),
            "Wrong city");
	}

    @Test
	public void testValidate() throws SAXException, JAXBException {

		final PurchaseOrderType purchaseOrder = objectFactory
				.createPurchaseOrderType();
		purchaseOrder.setShipTo(objectFactory.createUSAddress());
		purchaseOrder.setBillTo(objectFactory.createUSAddress());
		final JAXBElement<PurchaseOrderType> purchaseOrderElement = objectFactory
				.createPurchaseOrder(purchaseOrder);

		final SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(new StreamSource(
				getClass().getClassLoader().getResourceAsStream("po.xsd")));

		final Marshaller marshaller = context.createMarshaller();

		marshaller.setSchema(schema);

		final List<ValidationEvent> events = new LinkedList<ValidationEvent>();

		marshaller.setEventHandler(new ValidationEventHandler() {
			public boolean handleEvent(ValidationEvent event) {
				events.add(event);
				return true;
			}
		});
		marshaller.marshal(purchaseOrderElement, new DOMResult());

		Assertions.assertFalse(events.isEmpty(), "List of validation events must not be empty.");

		System.out.println(events.get(0));
	}
}
