package org.jvnet.jaxb.tests.namespace;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.test.namespace.b.BType;

import java.util.Arrays;

public class BTest {

    @Test
    public void testB() {
        BType b = new BType();
        XmlSchema schema = b.getClass().getPackage().getAnnotation(XmlSchema.class);
        XmlNs[] namespaces = schema.xmlns();
        Assertions.assertNotNull(namespaces);
        Assertions.assertEquals(2, namespaces.length);
        XmlNs aNs = Arrays.stream(namespaces).filter(ns -> "aprefix".equals(ns.prefix())).findFirst().orElse(null);
        Assertions.assertNotNull(aNs);
        Assertions.assertEquals("a", aNs.namespaceURI());
        Assertions.assertEquals("aprefix", aNs.prefix());
        XmlNs bNs = Arrays.stream(namespaces).filter(ns -> "b".equals(ns.prefix())).findFirst().orElse(null);
        Assertions.assertNotNull(bNs);
        Assertions.assertEquals("b", bNs.namespaceURI());
        Assertions.assertEquals("b", bNs.prefix());
    }
}
