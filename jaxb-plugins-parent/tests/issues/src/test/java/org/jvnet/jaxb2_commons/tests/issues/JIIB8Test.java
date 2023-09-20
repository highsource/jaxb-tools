package org.jvnet.jaxb2_commons.tests.issues;

import java.util.Arrays;

import org.junit.Assert;
import junit.framework.TestCase;

public class JIIB8Test extends TestCase {

	public void testCollectionSetters() throws Exception {

		final IssueJIIB8Type one = new IssueJIIB8Type();

		one.setValue(Arrays.asList("1", "2", "3"));

		Assert.assertEquals(3, one.getValue().size());

	}
}
