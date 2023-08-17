package org.jvnet.annox.parser.tests;

import javax.xml.bind.annotation.XmlRootElement;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.parser.XAnnotationParser;
import org.jvnet.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.annox.parser.exception.AnnotationStringParseException;

import junit.framework.TestCase;

public class JAXBDemoTest extends TestCase {

	public void testXMLRootElement() throws AnnotationStringParseException,
			AnnotationExpressionParseException {
		@SuppressWarnings("unchecked")

		// Parse annotation from the string
		XAnnotation<XmlRootElement> xannotation =
			(XAnnotation<XmlRootElement>) XAnnotationParser.INSTANCE.parse
				("@javax.xml.bind.annotation.XmlRootElement(name=\"foo\")");

		// Create an instance of the annotation 
		XmlRootElement xmlRootElement = xannotation.getResult();
		assertEquals("foo", xmlRootElement.name());
		assertEquals("##default", xmlRootElement.namespace());
		
		// Analyze the structure of the annotation
		assertEquals(String.class, xannotation.getFieldsMap().get("name").getType());
		assertEquals("##default", xannotation.getFieldsMap().get("namespace").getResult());
	}
}
