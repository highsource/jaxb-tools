package org.jvnet.jaxb2_commons.reflection.util.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.reflection.util.Accessor;
import org.jvnet.jaxb2_commons.reflection.util.FieldAccessor;

public class FieldAccessorTest {

	@Test
	public void testGetAndSet() throws URISyntaxException {
		final URI uri = new URI("urn:test");

		final Accessor<String> schemeAccessor = new FieldAccessor<String>(
				URI.class, "scheme", String.class);

		Assert.assertEquals("urn", schemeAccessor.get(uri));
		schemeAccessor.set(uri, "nru");
		Assert.assertEquals("nru", schemeAccessor.get(uri));
		Assert.assertEquals("nru", uri.getScheme());

	}

}