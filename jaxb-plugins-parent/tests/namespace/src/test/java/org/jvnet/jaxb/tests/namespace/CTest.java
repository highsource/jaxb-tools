package org.jvnet.jaxb.tests.namespace;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.test.namespace.a.A1Type;
import org.jvnet.jaxb.test.namespace.c.C1Type;
import org.jvnet.jaxb.test.namespace.c.CType;

import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.util.Arrays;

public class CTest {

    @Test
    public void testC() throws JAXBException {
        CType c = new CType();
        XmlSchema schema = c.getClass().getPackage().getAnnotation(XmlSchema.class);
        XmlNs[] namespaces = schema.xmlns();
        Assertions.assertEquals(3, namespaces.length);
        XmlNs aNs = Arrays.stream(namespaces).filter(ns -> "aprefix".equals(ns.prefix())).findFirst().orElse(null);
        Assertions.assertNotNull(aNs);
        Assertions.assertEquals("a", aNs.namespaceURI());
        Assertions.assertEquals("aprefix", aNs.prefix());
        XmlNs bNs = Arrays.stream(namespaces).filter(ns -> "bprefix".equals(ns.prefix())).findFirst().orElse(null);
        Assertions.assertNotNull(bNs);
        Assertions.assertEquals("b", bNs.namespaceURI());
        Assertions.assertEquals("bprefix", bNs.prefix());
        XmlNs cNs = Arrays.stream(namespaces).filter(ns -> "creal".equals(ns.prefix())).findFirst().orElse(null);
        Assertions.assertNotNull(cNs);
        Assertions.assertEquals("c", cNs.namespaceURI());
        Assertions.assertEquals("creal", cNs.prefix());

        c.setA(new A1Type());
        c.setC(new C1Type());

        JAXBElement<CType> jaxbElement
            = new JAXBElement<>( new QName("c", "cPart"), CType.class, c);
        JAXBContext context = JAXBContext.newInstance(CType.class.getPackage().getName());
        StringWriter writer = new StringWriter();
        context.createMarshaller().marshal(jaxbElement, writer);
        String marshalled = writer.toString();

        Assertions.assertNotNull(marshalled);
        String expectedA = "xmlns:aprefix=\"a\"";
        Assertions.assertTrue(marshalled.contains(expectedA));
        String expectedB = "xmlns:bprefix=\"b\"";
        Assertions.assertTrue(marshalled.contains(expectedB));
        String expectedC = "xmlns:creal=\"c\"";
        Assertions.assertTrue(marshalled.contains(expectedC));
    }
}
