package org.jvnet.jaxb.tests.annotate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

public class PackageAnnotationTest {

    @Test
    public void testBase() {
        Package p = mypackage.model.base.ObjectFactory.class.getPackage();
        Annotation a = p.getAnnotation(Deprecated.class);
        Assertions.assertNotNull(a);
        Assertions.assertEquals("4.0.3", ((Deprecated) a).since());
    }

    @Test
    public void testExtegration() {
        Package p = mypackage.model.extegration.ObjectFactory.class.getPackage();
        Annotation a = p.getAnnotation(Deprecated.class);
        Assertions.assertNotNull(a);
        Assertions.assertEquals("4.0.2", ((Deprecated) a).since());
    }

    @Test
    public void testIntegration() {
        Package p = mypackage.model.integration.ObjectFactory.class.getPackage();
        Annotation a = p.getAnnotation(Deprecated.class);
        Assertions.assertNotNull(a);
        Assertions.assertEquals("4.0.1", ((Deprecated) a).since());
    }
}
