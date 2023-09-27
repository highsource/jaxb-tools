package org.jvnet.jaxb.tests.issues;

import junit.framework.TestCase;

import org.junit.Assert;
import org.jvnet.jaxb.tests.issues.IssueJIIB14BaseClass;
import org.jvnet.jaxb.tests.issues.IssueJIIB14BaseInterfaceFour;
import org.jvnet.jaxb.tests.issues.IssueJIIB14BaseInterfaceOne;
import org.jvnet.jaxb.tests.issues.IssueJIIB14BaseInterfaceThree;
import org.jvnet.jaxb.tests.issues.IssueJIIB14BaseInterfaceTwo;

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
