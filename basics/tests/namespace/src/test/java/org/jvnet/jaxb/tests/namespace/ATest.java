package org.jvnet.jaxb.tests.namespace;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.test.namespace.a.AType;

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
