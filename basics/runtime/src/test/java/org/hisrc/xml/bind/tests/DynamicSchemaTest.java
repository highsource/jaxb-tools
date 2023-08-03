
package org.hisrc.xml.bind.tests;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.xml.bind.v2.WellKnownNamespace;

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
				.newInstance(WellKnownNamespace.XML_SCHEMA);
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
