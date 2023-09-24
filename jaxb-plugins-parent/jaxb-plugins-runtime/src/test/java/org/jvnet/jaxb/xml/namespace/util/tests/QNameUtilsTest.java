package org.jvnet.jaxb.xml.namespace.util.tests;

import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.xml.namespace.util.QNameUtils;

public class QNameUtilsTest {

	@Test
	public void producesCorrectKey() {
		Assert.assertEquals(null, QNameUtils.getKey(null));
		Assert.assertEquals("a", QNameUtils.getKey(new QName("a")));
		Assert.assertEquals("{b}a", QNameUtils.getKey(new QName("b", "a")));
		Assert.assertEquals("{b}c:a",
				QNameUtils.getKey(new QName("b", "a", "c")));
		Assert.assertEquals("c:a", QNameUtils.getKey(new QName("", "a", "c")));
	}
}
