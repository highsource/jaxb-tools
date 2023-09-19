package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Version;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IssueHJIII35Test extends TestCase {

	public void testEntityAnnotation() throws Exception {

		Assert.assertNotNull(IssueHJIII35Type.class.getMethod("getHjversion",
				new Class[0]).getAnnotation(Version.class));
	}

}
