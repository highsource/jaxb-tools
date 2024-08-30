package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JIIB20Test {

    @Test
	public void testException() throws Exception {

		final List<String> strings = Arrays.asList("a", "b", "c");
		final IssueJIIB20 one = new IssueJIIB20();
		one.setStrings(strings);
		Assertions.assertEquals(strings, one.getStrings());
		Assertions.assertSame(strings, one.getStrings());

	}
}
