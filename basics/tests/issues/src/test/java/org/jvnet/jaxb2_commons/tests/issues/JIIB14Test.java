package org.jvnet.jaxb2_commons.tests.issues;

import junit.framework.TestCase;

import org.junit.Assert;

public class JIIB14Test extends TestCase {

	public void testIssueJIIB14() throws Exception {
		Assert.assertEquals(IssueJIIB14BaseClass.class,
				IssueJIIB14Element.class.getSuperclass());
		Assert.assertTrue(IssueJIIB14BaseInterfaceOne.class
				.isAssignableFrom(IssueJIIB14Element.class));
		Assert.assertTrue(IssueJIIB14BaseInterfaceTwo.class
				.isAssignableFrom(IssueJIIB14Element.class));
		Assert.assertTrue(IssueJIIB14BaseInterfaceThree.class
				.isAssignableFrom(IssueJIIB14JAXBElement.class));
		Assert.assertTrue(IssueJIIB14BaseInterfaceFour.class
				.isAssignableFrom(IssueJIIB14JAXBElement.class));
	}
}
