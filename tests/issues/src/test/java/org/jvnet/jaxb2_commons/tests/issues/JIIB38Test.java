package org.jvnet.jaxb2_commons.tests.issues;

import org.junit.Assert;
import junit.framework.TestCase;

public class JIIB38Test extends TestCase {

	public void testException() throws Exception {

		final IssueJIIB38 one = new IssueJIIB38();
		final IssueJIIB38Type two = IssueJIIB38Type.A;

		Assert.assertTrue(one instanceof Cloneable);
		Assert.assertTrue(two instanceof Cloneable);

	}
}
