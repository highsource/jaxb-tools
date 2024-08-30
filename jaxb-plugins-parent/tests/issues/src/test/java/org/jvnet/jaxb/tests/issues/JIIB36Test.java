package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JIIB36Test {

	@Test
	public void runsWithoutException() {

		final IssueJIIB36 one = new IssueJIIB36();
		final IssueJIIB36 two = new IssueJIIB36();

		Assertions.assertTrue(one.equals(two));
		Assertions.assertTrue(two.equals(one));
		Assertions.assertEquals(one.hashCode(), two.hashCode());

	}
}
