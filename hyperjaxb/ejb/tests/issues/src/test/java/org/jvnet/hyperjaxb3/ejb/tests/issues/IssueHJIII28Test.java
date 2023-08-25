package org.jvnet.hyperjaxb3.ejb.tests.issues;

import javax.persistence.Column;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.jaxb.annox.reflect.AnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.DualAnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

public class IssueHJIII28Test extends TestCase {
	
	public void testLengthAnnotation() throws Exception {
		
		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();
		
		final ParameterizedAnnotatedElement annotatedElement = aef.getAnnotatedElement(IssueHJIII28ComplexType.class.getMethod("getIssueHJIII28"));
		
		final Column annotation = annotatedElement.getAnnotation(Column.class);
		
		Assert.assertEquals(1024, annotation.length());
		
		

		
	}

}
