package org.jvnet.jaxb.plugin.simplify.tests03;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Gh5Test {

	private JAXBContext context;

	@Before
	public void setUp() throws Exception {
		context = JAXBContext.newInstance(getClass().getPackage().getName());
	}

	@Test
	public void compiles() {
		final SimplifyReferencesPropertyAsReferencePropertyType item = new SimplifyReferencesPropertyAsReferencePropertyType();
		item.getBases();
        item.getBasesLength();
		item.getBaseElements();
        item.getBaseElementsLength();
	}

	@Test
	public void unmarshalls() throws Exception {

		@SuppressWarnings("unchecked")
		SimplifyReferencesPropertyAsReferencePropertyType value = ((JAXBElement<SimplifyReferencesPropertyAsReferencePropertyType>) context
				.createUnmarshaller()
				.unmarshal(
						getClass()
								.getResourceAsStream(
										"simplifyReferencesPropertyAsReferenceProperty.xml")))
				.getValue();

		Assert.assertEquals(3, value.getBasesLength());
		Assert.assertEquals(3, value.getBaseElementsLength());
	}
}
