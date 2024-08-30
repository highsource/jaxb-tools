package org.jvnet.jaxb.tests.annotate;

import generated.IssueJAP9Type;
import jakarta.xml.bind.annotation.XmlElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueJAP9TypeTest {

    @Test
    public void testIssueJAP9TypeAField() throws NoSuchFieldException {
        IssueJAP9Type type = new IssueJAP9Type();
        XmlElement ann = type.getClass().getDeclaredField("a").getAnnotation(XmlElement.class);
        Assertions.assertNull(ann);
    }
}
