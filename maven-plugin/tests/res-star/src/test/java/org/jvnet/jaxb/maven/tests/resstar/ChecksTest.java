package org.jvnet.jaxb.maven.tests.resstar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChecksTest {

    @Test
    public void testIncludedFound() throws ClassNotFoundException {
        String className = "other.example.org.Lison";
        Class.forName(className);
        className = "org.jvnet.jaxb2_commons.tests.one.ConcreteType";
        Class.forName(className);
    }

    @Test
    public void testNotIncludedNotFound() throws ClassNotFoundException {
        String className = "org.example.xs.PurchaseOrder";
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName(className),
            "Class " + className + " shouldn't have been found");
    }
}
