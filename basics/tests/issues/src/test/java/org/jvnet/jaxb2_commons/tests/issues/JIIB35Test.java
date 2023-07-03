package org.jvnet.jaxb2_commons.tests.issues;

import javax.xml.bind.JAXBContext;

import org.junit.Assert;
import junit.framework.TestCase;

import org.jvnet.jaxb2_commons.lang.ContextUtils;

public class JIIB35Test extends TestCase {

	public void testException() throws Exception {

		final ObjectFactory objectFactory = new ObjectFactory();

		final IssueJIIB35 one = objectFactory.createIssueJIIB35();
		final IssueJIIB35 two = objectFactory.createIssueJIIB35();
		final IssueJIIB35 three = objectFactory.createIssueJIIB35();
		final IssueJIIB35 four = objectFactory.createIssueJIIB35();

		final JAXBContext context = JAXBContext.newInstance(IssueJIIB35.class);

		final String alpha = ContextUtils.toString(context, one);
		two.hashCode();
		final String beta = ContextUtils.toString(context, two);
		three.equals(null);
		final String gamma = ContextUtils.toString(context, three);
		four.toString();
		final String delta = ContextUtils.toString(context, four);

		Assert.assertEquals(alpha, beta);
		Assert.assertEquals(beta, gamma);
		Assert.assertEquals(gamma, delta);

	}
}
