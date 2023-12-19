package org.jvnet.jaxb.maven.tests.resstar;

import org.junit.Assert;
import org.junit.Test;

public class ChecksTest {

    @Test
    public void testIncludedFound() throws ClassNotFoundException {
        String className = "other.example.org.Lison";
        Class.forName(className);
        className = "org.jvnet.jaxb2_commons.tests.one.ConcreteType";
        Class.forName(className);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testNotIncludedNotFound() throws ClassNotFoundException {
        String className = "org.example.xs.PurchaseOrder";
        Class.forName(className);
        Assert.fail("Class " + className + " shouldn't have been found");
    }
}
