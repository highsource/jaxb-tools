package org.jvnet.jaxb.plugin.simplify.tests03;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Test;

public class Gh1Test {

	@Test
	public void compiles()
	{
		final Gh1 item = new Gh1();
		item.getAs();
        item.getAsLength();
		item.getBs();
        item.getBsLength();
		item.getMixedContent();
        item.getMixedContentLength();
	}

	@Test
	public void contextIsSuccessfullyCreated() throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(Gh1.class);
		final Gh1 value = new Gh1();
		value.setAs(new String[]{ "a" });
		value.setBs(new int[]{ 2 });
		value.setMixedContent(new String[]{ "Test" });

		final StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(
				new JAXBElement<Gh1>(new QName("test"), Gh1.class, value), System.out);
		context.createMarshaller().marshal(
				new JAXBElement<Gh1>(new QName("test"), Gh1.class, value), sw);
		Assert.assertTrue(sw.toString().contains("Test"));
	}
}
