package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.NamedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueHJIII100Test {

    @Test
	public void testEntityAnnotation() throws Exception {

		Assertions.assertNotNull(IssueHJIII100Type.class
				.getAnnotation(NamedQuery.class));

        Assertions.assertEquals(
				1,
				IssueHJIII100Type.class.getAnnotation(NamedQuery.class).hints().length);
	}

}
