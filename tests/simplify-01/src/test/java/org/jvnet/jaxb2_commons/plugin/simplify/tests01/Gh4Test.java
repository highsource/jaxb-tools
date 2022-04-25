package org.jvnet.jaxb2_commons.plugin.simplify.tests01;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Gh4Test {

	private JAXBContext context;

	@Before
	public void setUp() throws Exception {
		context = JAXBContext.newInstance(getClass().getPackage().getName());
	}

	@Test
	public void compiles() {
		final SimplifyReferencesPropertyAsElementPropertyType item = new SimplifyReferencesPropertyAsElementPropertyType();
		item.getBases();
		item.getBaseElements();

	}

	@Test
	public void unmarshalls() throws Exception {

		@SuppressWarnings("unchecked")
		SimplifyReferencesPropertyAsElementPropertyType value = ((JAXBElement<SimplifyReferencesPropertyAsElementPropertyType>) context
				.createUnmarshaller()
				.unmarshal(
						getClass()
								.getResourceAsStream(
										"simplifyReferencesPropertyAsElementProperty.xml")))
				.getValue();

		Assert.assertEquals(3, value.getBases().size());
		Assert.assertEquals(3, value.getBaseElements().size());
	}
}
