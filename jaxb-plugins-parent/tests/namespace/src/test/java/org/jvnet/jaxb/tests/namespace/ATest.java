package org.jvnet.jaxb.tests.namespace;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.test.namespace.a.AType;

public class ATest {

    @Test
    public void testA() {
        AType a = new AType();
        XmlSchema schema = a.getClass().getPackage().getAnnotation(XmlSchema.class);
        XmlNs[] namespaces = schema.xmlns();
        Assert.assertNotNull(namespaces);
        Assert.assertEquals(1, namespaces.length);
        Assert.assertEquals("a", namespaces[0].namespaceURI());
        Assert.assertEquals("a", namespaces[0].prefix());
    }
}
