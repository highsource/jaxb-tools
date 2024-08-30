package org.jvnet.jaxb.annox.parser.tests;

import jakarta.xml.bind.annotation.XmlRootElement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationStringParseException;

public class JAXBDemoTest {

    @Test
	public void testXMLRootElement() throws AnnotationStringParseException,
			AnnotationExpressionParseException {
		@SuppressWarnings("unchecked")

		// Parse annotation from the string
		XAnnotation<XmlRootElement> xannotation =
			(XAnnotation<XmlRootElement>) XAnnotationParser.INSTANCE.parse
				("@jakarta.xml.bind.annotation.XmlRootElement(name=\"foo\")");

		// Create an instance of the annotation
		XmlRootElement xmlRootElement = xannotation.getResult();
		Assertions.assertEquals("foo", xmlRootElement.name());
        Assertions.assertEquals("##default", xmlRootElement.namespace());

		// Analyze the structure of the annotation
        Assertions.assertEquals(String.class, xannotation.getFieldsMap().get("name").getType());
        Assertions.assertEquals("##default", xannotation.getFieldsMap().get("namespace").getResult());
	}
}
