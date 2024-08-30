package org.jvnet.hyperjaxb3.sb.tests;

import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtributeDefinitionTest {

	@Test
	public void testUnmarshall() throws Exception {
		JAXBContext context = JAXBContext
				.newInstance(AttributeDefinition.class);

		AttributeDefinition<Integer> attributeDefinition = (AttributeDefinition<Integer>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("attribute.xml"));

		Assertions.assertEquals(Integer.valueOf(5), attributeDefinition.getValue());

	}

    @Test
	public void testSimpleUnmarshall() throws Exception {
		JAXBContext context = JAXBContext
				.newInstance(SimpleAttributeDefinition.class);

		SimpleAttributeDefinition<Integer> attributeDefinition = (SimpleAttributeDefinition<Integer>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("simpleAttribute.xml"));

		Assertions.assertEquals(Integer.valueOf(5), attributeDefinition.getValue());

	}

}
