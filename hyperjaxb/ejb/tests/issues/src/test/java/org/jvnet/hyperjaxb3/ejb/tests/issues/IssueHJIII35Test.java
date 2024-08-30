package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueHJIII35Test {

    @Test
	public void testEntityAnnotation() throws Exception {

		Assertions.assertNotNull(IssueHJIII35Type.class.getMethod("getHjversion",
				new Class[0]).getAnnotation(Version.class));
	}

}
