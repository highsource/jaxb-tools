package org.jvnet.jaxb2_commons.tests.issues;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JIIB36Test extends TestCase {

	public void testCollectionSetters() throws Exception {

		final IssueJIIB36 one = new IssueJIIB36();
		final IssueJIIB36 two = new IssueJIIB36();

		Assert.assertTrue(one.equals(two));
		Assert.assertTrue(two.equals(one));
		Assert.assertEquals(one.hashCode(), two.hashCode());

	}
}
