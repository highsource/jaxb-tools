package org.jvnet.jaxb2.maven2.net.tests;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2.maven2.net.CompositeURILastModifiedResolver;
import org.jvnet.jaxb2.maven2.net.JarURILastModifiedResolver;
import org.jvnet.jaxb2.maven2.net.URILastModifiedResolver;

import java.net.URI;
import java.net.URISyntaxException;

public class URILastModifiedResolverTest {

	@Test
	public void getsFileURIFromJarFileURICorrectly() throws URISyntaxException {
		// Test with a class that resides in a jar (Assert.class).
		// Note that Object.class is NOT in a jar but in a module under Java9 or later.
		final URI jarURI = Assert.class.getResource("Assert.class").toURI();
		final String jarURIString = jarURI.toString();
		final String partJarURIString = jarURIString.substring(0,
				jarURIString.indexOf(JarURILastModifiedResolver.SEPARATOR));
		final URI partJarURI = new URI(partJarURIString);
		final URILastModifiedResolver resolver = new CompositeURILastModifiedResolver(
				new SystemStreamLog());
		final URI fileURI = getClass().getResource(
				getClass().getSimpleName() + ".class").toURI();

		Assert.assertNotNull(resolver.getLastModified(jarURI));
		Assert.assertNotNull(resolver.getLastModified(partJarURI));
		Assert.assertNotNull(resolver.getLastModified(fileURI));

		// Switch to true to tests HTTP/HTTPs
		boolean online = false;
		if (online) {
			final URI httpsURI = new URI("https://ya.ru/");
			final URI httpURI = new URI("http://schemas.opengis.net/ogc_schema_updates.rss");
			Assert.assertNotNull(resolver.getLastModified(httpsURI));
			Assert.assertNotNull(resolver.getLastModified(httpURI));
		}
	}
}
