package org.jvnet.jaxb2_commons.tests.issues.tests;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.tests.issues.ObjectFactory;

public class Issue1Test {

	@Test
	public void testPackageInfoIsAnnotated() {
		final XmlSchema xmlSchema = ObjectFactory.class.getPackage()
				.getAnnotation(XmlSchema.class);
		Assert.assertEquals(XmlNsForm.QUALIFIED, xmlSchema.elementFormDefault());
	}

}
