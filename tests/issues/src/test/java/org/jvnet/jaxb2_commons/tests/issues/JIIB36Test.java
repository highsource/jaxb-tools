package org.jvnet.jaxb2_commons.tests.issues;

import org.junit.Assert;
import org.junit.Test;

public class JIIB36Test {

	@Test
	public void runsWithoutException() {

		final IssueJIIB36 one = new IssueJIIB36();
		final IssueJIIB36 two = new IssueJIIB36();

		Assert.assertTrue(one.equals(two));
		Assert.assertTrue(two.equals(one));
		Assert.assertEquals(one.hashCode(), two.hashCode());

	}
}
