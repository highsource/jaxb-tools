package org.jvnet.hyperjaxb3.ejb.tests.issues;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueHJIII94Test {

    @Test
    public void testEntityAnnotationId() throws Exception {

        Assertions.assertNotNull(IssueHJIII94Type.class.getMethod("getHjid",
            new Class[0]).getAnnotation(Id.class));

        Assertions.assertThrows(NoSuchMethodException.class, () ->
            IssueHJIII94SubType.class.getDeclaredMethod("getHjid", new Class[0]));
    }

    @Test
    public void testEntityAnnotationVersion() throws Exception {

        Assertions.assertNotNull(IssueHJIII94Type.class.getMethod("getHjversion",
            new Class[0]).getAnnotation(Version.class));

        Assertions.assertThrows(NoSuchMethodException.class, () ->
            IssueHJIII94SubType.class.getDeclaredMethod("getHjversion", new Class[0]));
    }

}
