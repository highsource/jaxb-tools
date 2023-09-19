package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Id;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IssueHJIII41Test extends TestCase {

	public void testEntityAnnotation() throws Exception {

		Assert.assertNotNull(IssueHJIII41ParentType.class.getMethod("getId",
				new Class[0]).getAnnotation(Id.class));

		try {
			IssueHJIII41ChildType.class.getMethod("getHjid", new Class[0]);
			Assert.fail("Expected exception.");
		} catch (NoSuchMethodException nsmex) {
			Assert.assertTrue(true);
		}
	}

}
