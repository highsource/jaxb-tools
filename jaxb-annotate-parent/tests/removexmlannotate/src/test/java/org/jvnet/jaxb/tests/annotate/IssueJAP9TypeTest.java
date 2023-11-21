package org.jvnet.jaxb.tests.annotate;

import generated.IssueJAP9Type;
import jakarta.xml.bind.annotation.XmlElement;
import org.junit.Assert;
import org.junit.Test;

import jakarta.xml.bind.annotation.XmlMimeType;
import java.lang.reflect.Field;

public class IssueJAP9TypeTest {

    @Test
    public void testIssueJAP9TypeAField() throws NoSuchFieldException {
        IssueJAP9Type type = new IssueJAP9Type();
        XmlElement ann = type.getClass().getDeclaredField("a").getAnnotation(XmlElement.class);
        Assert.assertNull(ann);
    }
}
