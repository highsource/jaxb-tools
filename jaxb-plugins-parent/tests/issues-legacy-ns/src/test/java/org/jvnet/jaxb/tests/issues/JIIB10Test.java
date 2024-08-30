package org.jvnet.jaxb.tests.issues;

import java.lang.reflect.Field;

import jakarta.xml.bind.annotation.XmlAnyElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JIIB10Test {

    @Test
	public void testXmlAnyElementLax() throws Exception {

		final Field contentField = IssueJIIB10Type.class.getDeclaredField("content");
		final XmlAnyElement xmlAnyElementAnnotation = contentField
				.getAnnotation(XmlAnyElement.class);
		Assertions.assertTrue(xmlAnyElementAnnotation.lax());
	}
}
