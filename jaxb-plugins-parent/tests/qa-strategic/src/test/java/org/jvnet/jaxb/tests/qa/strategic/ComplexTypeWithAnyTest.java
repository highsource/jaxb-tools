package org.jvnet.jaxb.tests.qa.strategic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

public class ComplexTypeWithAnyTest {

    @Test
    public void testToString() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key"), "value");

        String c1ToString = c1.toString();
        Assertions.assertTrue(c1ToString.startsWith("org.jvnet.jaxb.tests.qa.strategic.ComplexTypeWithAny"));
        Assertions.assertTrue(c1ToString.contains("otherAttributes={key=value}"));
    }

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

    @Test
    public void testCopyTo() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key"), "value");

        ComplexTypeWithAny c2 = new ComplexTypeWithAny();
        c1.copyTo(c2);

        Assertions.assertEquals(c1, c2);
        Assertions.assertTrue(c2.getOtherAttributes().containsKey(new QName("key")));
    }

    @Test
    public void testMerge() {
        ComplexTypeWithAny c1 = new ComplexTypeWithAny();
        c1.getOtherAttributes().put(new QName("key1"), "value1");
        ComplexTypeWithAny c2 = new ComplexTypeWithAny();
        c2.getOtherAttributes().put(new QName("key2"), "value2");

        ComplexTypeWithAny c3 = new ComplexTypeWithAny();
        c3.mergeFrom(c1, c2);

        Assertions.assertEquals(c1, c3);
        Assertions.assertNotEquals(c2, c3);
        Assertions.assertEquals(1, c3.getOtherAttributes().size());
        Assertions.assertTrue(c3.getOtherAttributes().containsKey(new QName("key1")));
    }
}
