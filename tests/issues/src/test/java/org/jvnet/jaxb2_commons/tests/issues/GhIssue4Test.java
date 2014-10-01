package org.jvnet.jaxb2_commons.tests.issues;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Test;

public class GhIssue4Test {

	@Test
	public void contextIsSuccessfullyCreated() throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(GhIssue4.class);
		final GhIssue4 value = new GhIssue4();
		value.getA().add("a");
		value.getB().add(2);
		value.getcontent().add("Test");

		final StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(
				new JAXBElement<GhIssue4>(new QName("test"), GhIssue4.class,
						value), sw);
		Assert.assertTrue(sw.toString().endsWith(
				"<test><a>a</a><b>2</b>Test</test>"));
	}
}
