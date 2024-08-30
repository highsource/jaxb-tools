package org.jvnet.jaxb.tests.namespace;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.test.namespace.a.AType;

public class ATest {

    @Test
    public void testA() {
        AType a = new AType();
        XmlSchema schema = a.getClass().getPackage().getAnnotation(XmlSchema.class);
        XmlNs[] namespaces = schema.xmlns();
        Assertions.assertNotNull(namespaces);
        Assertions.assertEquals(1, namespaces.length);
        Assertions.assertEquals("a", namespaces[0].namespaceURI());
        Assertions.assertEquals("a", namespaces[0].prefix());
    }
}
