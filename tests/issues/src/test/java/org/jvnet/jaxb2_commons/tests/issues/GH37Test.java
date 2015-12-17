package org.jvnet.jaxb2_commons.tests.issues;

import org.junit.Assert;
import org.junit.Test;

public class GH37Test {

	@Test
	public void considersDefaultValues() {
		final IssueGH37Type left = new IssueGH37Type();
		final IssueGH37Type right = new IssueGH37Type();
		final IssueGH37Type result = new IssueGH37Type();
		result.mergeFrom(left, right);
		Assert.assertTrue(result.isTestBoolean());
	}

}
