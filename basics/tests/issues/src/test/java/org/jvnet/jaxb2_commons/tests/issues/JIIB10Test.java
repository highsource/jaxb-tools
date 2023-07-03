package org.jvnet.jaxb2_commons.tests.issues;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlAnyElement;

import junit.framework.TestCase;

import org.junit.Assert;

public class JIIB10Test extends TestCase {

	public void testXmlAnyElementLax() throws Exception {

		final Field contentField = IssueJIIB10Type.class.getDeclaredField("content");
		final XmlAnyElement xmlAnyElementAnnotation = contentField
				.getAnnotation(XmlAnyElement.class);
		Assert.assertTrue(xmlAnyElementAnnotation.lax());
	}
}
