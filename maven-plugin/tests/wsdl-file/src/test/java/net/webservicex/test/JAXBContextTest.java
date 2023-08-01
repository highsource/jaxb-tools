package net.webservicex.test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import junit.framework.Assert;
import net.webservicex.GetWeather;

public class JAXBContextTest {

	public static final String CONTEXT_PATH = GetWeather.class.getPackage().getName();

	@Test
	public void successfullyCreatesMarshallerAndUnmarshaller() throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);
		Assert.assertNotNull(context.createMarshaller());
		Assert.assertNotNull(context.createUnmarshaller());

	}

}
