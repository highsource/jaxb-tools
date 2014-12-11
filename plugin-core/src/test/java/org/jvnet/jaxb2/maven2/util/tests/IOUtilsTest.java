package org.jvnet.jaxb2.maven2.util.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2.maven2.util.IOUtils;

public class IOUtilsTest {

	@Test
	public void getsFileURIFromJarFileURICorrectly() throws URISyntaxException,
			MalformedURLException, IOException {
		final URI fileURI = new URI("file:/C:/Test/test.jar");

		// URLConnection openConnection = new URL(uriString).openConnection();
		Assert.assertEquals(fileURI, IOUtils.getMainURIFromJarURI(new URI("jar:file:/C:/Test/test.jar!/a.xsd")));
	}
}
