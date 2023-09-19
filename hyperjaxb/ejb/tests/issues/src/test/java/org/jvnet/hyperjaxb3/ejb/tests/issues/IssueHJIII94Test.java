package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Id;
import jakarta.persistence.Version;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IssueHJIII94Test extends TestCase {

	public void testEntityAnnotation() throws Exception {

		Assert.assertNotNull(IssueHJIII94Type.class.getMethod("getHjid",
				new Class[0]).getAnnotation(Id.class));

		Assert.assertNotNull(IssueHJIII94Type.class.getMethod("getHjversion",
				new Class[0]).getAnnotation(Version.class));

		try {
			IssueHJIII94SubType.class.getDeclaredMethod("getHjid", new Class[0]);
			Assert.fail("Expected exception.");
		} catch (NoSuchMethodException nsmex) {
			Assert.assertTrue(true);
		}
		try {
			IssueHJIII94SubType.class.getDeclaredMethod("getHjversion", new Class[0]);
			Assert.fail("Expected exception.");
		} catch (NoSuchMethodException nsmex) {
			Assert.assertTrue(true);
		}
	}

}
