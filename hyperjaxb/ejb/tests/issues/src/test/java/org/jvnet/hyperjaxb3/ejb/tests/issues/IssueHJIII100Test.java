package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.NamedQuery;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IssueHJIII100Test extends TestCase {

	public void testEntityAnnotation() throws Exception {

		Assert.assertNotNull(IssueHJIII100Type.class
				.getAnnotation(NamedQuery.class));

		Assert.assertEquals(
				1,
				IssueHJIII100Type.class.getAnnotation(NamedQuery.class).hints().length);
	}

}
