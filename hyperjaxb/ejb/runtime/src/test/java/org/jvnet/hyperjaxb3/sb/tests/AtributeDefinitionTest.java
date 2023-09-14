package org.jvnet.hyperjaxb3.sb.tests;

import jakarta.xml.bind.JAXBContext;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AtributeDefinitionTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testUnmarshall() throws Exception {
		JAXBContext context = JAXBContext
				.newInstance(AttributeDefinition.class);

		AttributeDefinition<Integer> attributeDefinition = (AttributeDefinition<Integer>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("attribute.xml"));

		Assert.assertEquals(Integer.valueOf(5), attributeDefinition.getValue());

	}
	
	public void testSimpleUnmarshall() throws Exception {
		JAXBContext context = JAXBContext
				.newInstance(SimpleAttributeDefinition.class);

		SimpleAttributeDefinition<Integer> attributeDefinition = (SimpleAttributeDefinition<Integer>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("simpleAttribute.xml"));

		Assert.assertEquals(Integer.valueOf(5), attributeDefinition.getValue());

	}
	
}
