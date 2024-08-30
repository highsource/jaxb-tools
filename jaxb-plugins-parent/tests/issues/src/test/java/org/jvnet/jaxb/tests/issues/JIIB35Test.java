package org.jvnet.jaxb.tests.issues;

import jakarta.xml.bind.JAXBContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.lang.ContextUtils;

public class JIIB35Test {

    @Test
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

		Assertions.assertEquals(alpha, beta);
		Assertions.assertEquals(beta, gamma);
		Assertions.assertEquals(gamma, delta);

	}
}
