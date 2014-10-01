package org.jvnet.jaxb2_commons.tests.one;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Test;

public class JavaTypeExampleTest {

	@Test
	public void rountripsBoolean() throws JAXBException {
		final JAXBContext context = JAXBContext
				.newInstance(JavaTypeExample.class);

		final JavaTypeExample value = new JavaTypeExample();
		value.setCustomBooleanProperty(true);
		final StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(
				new JAXBElement<JavaTypeExample>(new QName("test"),
						JavaTypeExample.class, value), sw);
		Assert.assertTrue(sw.toString().contains(">true<"));
//		System.out.println(sw.toString());
	}
}
