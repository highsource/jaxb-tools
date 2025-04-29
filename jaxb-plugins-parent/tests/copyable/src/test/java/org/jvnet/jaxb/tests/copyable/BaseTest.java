package org.jvnet.jaxb.tests.copyable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class BaseTest {

    @Test
    public void testBase() throws DatatypeConfigurationException {
        XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        byte[] textBytes = "String".getBytes(StandardCharsets.UTF_8);
        Base a = new Base();
        a.setA("String");
        a.setB(xgc);
        a.setC(xgc);
        a.setD(xgc);
        a.setE(textBytes);
        a.getListOfString().addAll(Arrays.asList("a", "b", "c"));
        a.getListOfString().addAll(Arrays.asList("e", "f", "g"));

        Base b = new Base();
        a.copyTo(b);

        Assertions.assertEquals("String", b.getA());
        Assertions.assertEquals(xgc, b.getB());
        Assertions.assertEquals(xgc, b.getC());
        Assertions.assertEquals(xgc, b.getD());
        Assertions.assertArrayEquals(textBytes, b.getE());
        Assertions.assertNotNull(b.getListOfString());
        Assertions.assertEquals(6, b.getListOfString().size());
        Assertions.assertTrue(b.getListOfString().containsAll(Arrays.asList("a", "b", "c", "e", "f", "g")));
    }
}
