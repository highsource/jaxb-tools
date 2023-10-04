
package org.hisrc.xml.bind.tests;

import java.io.IOException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.MarshalException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;

public class DynamicSchemaTest {

	@XmlRootElement
	public static class A {
		@XmlAttribute(required = true)
		public String name;

		public A() {
		}

		public A(String name) {
			this.name = name;
		}
	}

	@Test(expected = MarshalException.class)
	public void generatesAndUsesSchema() throws JAXBException, IOException,
			SAXException {
		final JAXBContext context = JAXBContext.newInstance(A.class);
		final DOMResult result = new DOMResult();
		result.setSystemId("schema.xsd");
		context.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri,
					String suggestedFileName) {
				return result;
			}
		});

		@SuppressWarnings("deprecation")
		final SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(new DOMSource(result
				.getNode()));

		final Marshaller marshaller = context.createMarshaller();
		marshaller.setSchema(schema);
		// Works
		marshaller.marshal(new A("works"), System.out);
		// Fails
		marshaller.marshal(new A(null), System.out);
	}
}
