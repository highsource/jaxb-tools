package org.jvnet.jaxb.tests.annotate;

import generated.IssueJAP9Type;
import org.junit.Assert;
import org.junit.Test;

import jakarta.xml.bind.annotation.XmlMimeType;
import java.lang.reflect.Field;

public class IssueJAP9TypeTest {

    @Test
    public void testIssueJAP9TypeAField() throws NoSuchFieldException {
        String valueInXsd = "[0-9]+\\.[0-9]{1,2}\\.[0-9]{4}\\.[0-9]+";
        IssueJAP9Type type = new IssueJAP9Type();
        Field[] fields = type.getClass().getDeclaredFields();
        XmlMimeType ann = type.getClass().getDeclaredField("a").getAnnotation(XmlMimeType.class);
        Assert.assertEquals(valueInXsd, ann.value());
    }

    @Test
    public void testIssueJAP9TypeBField() throws NoSuchFieldException {
        String valueInXsd = "[0-9]+\\\\.[0-9]{1,2}\\\\.[0-9]{4}\\\\.[0-9]+";
        IssueJAP9Type type = new IssueJAP9Type();
        XmlMimeType ann = type.getClass().getDeclaredField("b").getAnnotation(XmlMimeType.class);
        Assert.assertEquals(valueInXsd, ann.value());
    }

    @Test
    public void testIssueJAP9TypeCField() throws NoSuchFieldException {
        String valueInXsd = "NoEscapedBackSlashes\"/Here";
        IssueJAP9Type type = new IssueJAP9Type();
        XmlMimeType ann = type.getClass().getDeclaredField("c").getAnnotation(XmlMimeType.class);
        Assert.assertEquals(valueInXsd, ann.value());
    }
}
