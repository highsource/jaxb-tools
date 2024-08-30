package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JIIB38Test {

    @Test
	public void testException() throws Exception {

		final IssueJIIB38 one = new IssueJIIB38();
		final IssueJIIB38Type two = IssueJIIB38Type.A;

		Assertions.assertTrue(one instanceof Cloneable);
		Assertions.assertTrue(two instanceof Cloneable);

	}
}
