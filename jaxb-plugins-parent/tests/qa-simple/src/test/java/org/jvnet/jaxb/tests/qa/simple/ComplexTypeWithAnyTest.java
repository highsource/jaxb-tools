package org.jvnet.jaxb.tests.qa.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

public class ComplexTypeWithAnyTest {

    /*@Test
    public void testToString() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key"), "value");

        String c1ToString = c1.toString();
        Assertions.assertTrue(c1ToString.startsWith("org.jvnet.jaxb.tests.qa.strategic.ComplexTypeWithAny"));
        Assertions.assertTrue(c1ToString.contains("otherAttributes={key=value}"));
    }*/

    @Test
    public void testEquals() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key"), "value");

        ComplexTypeWithAny c2 = new ComplexTypeWithAny();
        c2.getOtherAttributes().put(new QName("key"), "value");

        Assertions.assertEquals(c1, c2);
    }

    @Test
    public void testHashCode() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key"), "value");
        ComplexTypeWithAny c2 = new ComplexTypeWithAny();
        c2.getOtherAttributes().put(new QName("key"), "value2");

        Assertions.assertNotEquals(c1, c2);

        int c1hash = c1.hashCode();
        int c2hash = c2.hashCode();
        Assertions.assertNotEquals(c1hash, c2hash);
    }
}
