package org.jvnet.jaxb.tests.namespace;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.test.namespace.b.BType;

import java.util.Arrays;

public class BTest {

    @Test
    public void testB() {
        BType b = new BType();
        XmlSchema schema = b.getClass().getPackage().getAnnotation(XmlSchema.class);
        XmlNs[] namespaces = schema.xmlns();
        Assert.assertNotNull(namespaces);
        Assert.assertEquals(2, namespaces.length);
        XmlNs aNs = Arrays.stream(namespaces).filter(ns -> "aprefix".equals(ns.prefix())).findFirst().orElse(null);
        Assert.assertNotNull(aNs);
        Assert.assertEquals("a", aNs.namespaceURI());
        Assert.assertEquals("aprefix", aNs.prefix());
        XmlNs bNs = Arrays.stream(namespaces).filter(ns -> "b".equals(ns.prefix())).findFirst().orElse(null);
        Assert.assertNotNull(bNs);
        Assert.assertEquals("b", bNs.namespaceURI());
        Assert.assertEquals("b", bNs.prefix());
    }
}
