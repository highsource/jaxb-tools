package org.jvnet.jaxb.xml.namespace.util.tests;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.xml.namespace.util.QNameUtils;

public class QNameUtilsTest {

	@Test
	public void producesCorrectKey() {
		Assertions.assertEquals(null, QNameUtils.getKey(null));
        Assertions.assertEquals("a", QNameUtils.getKey(new QName("a")));
        Assertions.assertEquals("{b}a", QNameUtils.getKey(new QName("b", "a")));
        Assertions.assertEquals("{b}c:a",
				QNameUtils.getKey(new QName("b", "a", "c")));
        Assertions.assertEquals("c:a", QNameUtils.getKey(new QName("", "a", "c")));
	}
}
