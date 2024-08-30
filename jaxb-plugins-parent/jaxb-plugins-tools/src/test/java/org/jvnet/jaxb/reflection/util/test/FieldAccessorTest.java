package org.jvnet.jaxb.reflection.util.test;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.reflection.util.Accessor;
import org.jvnet.jaxb.reflection.util.FieldAccessor;

public class FieldAccessorTest {

	@Test
	public void testGetAndSet() throws URISyntaxException {
		final URIInternalTest uri = new URIInternalTest("urn:test");

		final Accessor<String> schemeAccessor = new FieldAccessor<String>(
				URIInternalTest.class, "scheme", String.class);

		Assertions.assertEquals("urn", schemeAccessor.get(uri));
		schemeAccessor.set(uri, "nru");
		Assertions.assertEquals("nru", schemeAccessor.get(uri));
		Assertions.assertEquals("nru", uri.getScheme());

	}

}
