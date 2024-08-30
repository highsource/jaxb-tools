package org.jvnet.jaxb.tests.issues;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class JIIB8Test {

    @Test
	public void testCollectionSetters() throws Exception {

		final IssueJIIB8Type one = new IssueJIIB8Type();

		one.setValue(Arrays.asList("1", "2", "3"));

		Assertions.assertEquals(3, one.getValue().size());

	}
}
