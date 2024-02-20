package org.jvnet.jaxb.tests.fluentapi;

import generated.Base;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals("String", b.getA());
        Assert.assertEquals(xgc, b.getB());
        Assert.assertEquals(xgc, b.getC());
        Assert.assertEquals(xgc, b.getD());
        Assert.assertEquals(textBytes, b.getE());
        Assert.assertNotNull(b.getListOfString());
        Assert.assertEquals(6, b.getListOfString().size());
        Assert.assertTrue(b.getListOfString().containsAll(Arrays.asList("a", "b", "c", "e", "f", "g")));
    }
}
