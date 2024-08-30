package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JIIB14Test {

    @Test
	public void testIssueJIIB14() throws Exception {
		Assertions.assertEquals(IssueJIIB14BaseClass.class,
				IssueJIIB14Element.class.getSuperclass());
		Assertions.assertTrue(IssueJIIB14BaseInterfaceOne.class
				.isAssignableFrom(IssueJIIB14Element.class));
		Assertions.assertTrue(IssueJIIB14BaseInterfaceTwo.class
				.isAssignableFrom(IssueJIIB14Element.class));
		Assertions.assertTrue(IssueJIIB14BaseInterfaceThree.class
				.isAssignableFrom(IssueJIIB14JAXBElement.class));
		Assertions.assertTrue(IssueJIIB14BaseInterfaceFour.class
				.isAssignableFrom(IssueJIIB14JAXBElement.class));
	}
}
