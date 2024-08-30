package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Id;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueHJIII41Test {

    @Test
	public void testEntityAnnotation() throws Exception {

		Assertions.assertNotNull(IssueHJIII41ParentType.class.getMethod("getId",
				new Class[0]).getAnnotation(Id.class));

        Assertions.assertThrows(NoSuchMethodException.class, () ->
			IssueHJIII41ChildType.class.getMethod("getHjid", new Class[0]));
	}

}
