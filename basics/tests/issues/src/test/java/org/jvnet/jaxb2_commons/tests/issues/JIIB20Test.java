package org.jvnet.jaxb2_commons.tests.issues;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

public class JIIB20Test extends TestCase {

	public void testException() throws Exception {

		final List<String> strings = Arrays.asList("a", "b", "c");
		final IssueJIIB20 one = new IssueJIIB20();
		one.setStrings(strings);
		Assert.assertEquals(strings, one.getStrings());
		Assert.assertSame(strings, one.getStrings());

	}
}
