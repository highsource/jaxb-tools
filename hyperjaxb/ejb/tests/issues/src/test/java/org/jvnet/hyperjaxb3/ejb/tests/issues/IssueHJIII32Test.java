package org.jvnet.hyperjaxb3.ejb.tests.issues;

import javax.persistence.Entity;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hyperjaxb3.ejb.tests.issuesignored.IssueHJIII32ComplexType;

public class IssueHJIII32Test extends TestCase {
	
	public void testEntityAnnotation() throws Exception {
		
		Assert.assertNull(IssueHJIII32ComplexType.class.getAnnotation(Entity.class));
		
		

		
	}

}
