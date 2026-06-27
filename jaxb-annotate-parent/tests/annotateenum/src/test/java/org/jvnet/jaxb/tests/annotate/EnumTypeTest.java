package org.jvnet.jaxb.tests.annotate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import org.example.sample.EnumType;
import org.jvnet.jaxb.plugin.annotate.tests.annotations.SampleAnnotation;

public class EnumTypeTest {

    @Test
    public void testEnum() {
        Class<EnumType> c = EnumType.class;
        Annotation a = c.getAnnotation(SampleAnnotation.class);
        Assertions.assertNotNull(a);
    }
}
