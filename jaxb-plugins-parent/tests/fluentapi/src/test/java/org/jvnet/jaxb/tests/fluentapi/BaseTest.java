package org.jvnet.jaxb.tests.fluentapi;

import generated.Base;
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
        Base b = new Base()
            .withA("String")
            .withB(xgc)
            .withC(xgc)
            .withD(xgc)
            .withE(textBytes)
            .withListOfString("a", "b", "c")
            .withListOfString(Arrays.asList("e", "f", "g"));

        Assertions.assertEquals("String", b.getA());
        Assertions.assertEquals(xgc, b.getB());
        Assertions.assertEquals(xgc, b.getC());
        Assertions.assertEquals(xgc, b.getD());
        Assertions.assertEquals(textBytes, b.getE());
        Assertions.assertNotNull(b.getListOfString());
        Assertions.assertEquals(6, b.getListOfString().size());
        Assertions.assertTrue(b.getListOfString().containsAll(Arrays.asList("a", "b", "c", "e", "f", "g")));
    }
}
