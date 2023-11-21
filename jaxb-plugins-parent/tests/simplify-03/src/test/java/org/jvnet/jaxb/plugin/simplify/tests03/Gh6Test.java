package org.jvnet.jaxb.plugin.simplify.tests03;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Gh6Test {

	private JAXBContext context;

	@Before
	public void setUp() throws Exception {
		context = JAXBContext.newInstance(getClass().getPackage().getName());
	}

	@Test
	public void compiles() {
		final SimplifyElementsPropertyAsElementPropertyType item = new SimplifyElementsPropertyAsElementPropertyType();
		item.getInts();
        item.getIntsLength();
		item.getStrings();
        item.getStringsLength();
	}

	public void testElementsPropertyAsElementPropertyType() throws Exception {

		@SuppressWarnings("unchecked")
		SimplifyElementsPropertyAsElementPropertyType value = ((JAXBElement<SimplifyElementsPropertyAsElementPropertyType>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("simplifyElementsPropertyAsElementProperty.xml")))
				.getValue();

		Assert.assertEquals(3, value.getStringsLength());
		Assert.assertEquals(3, value.getIntsLength());
	}
}
