package org.jvnet.jaxb.tests.copyable;

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

        Assert.assertEquals("String", b.getA());
        Assert.assertEquals(xgc, b.getB());
        Assert.assertEquals(xgc, b.getC());
        Assert.assertEquals(xgc, b.getD());
        Assert.assertArrayEquals(textBytes, b.getE());
        Assert.assertNotNull(b.getListOfString());
        Assert.assertEquals(6, b.getListOfString().size());
        Assert.assertTrue(b.getListOfString().containsAll(Arrays.asList("a", "b", "c", "e", "f", "g")));
    }
}
