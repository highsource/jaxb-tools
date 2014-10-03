package org.jvnet.jaxb2.maven2.resolver.tools.tests;

import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

public class MavenCatalogResolverTest {

	@Test
	public void checkReenterability() throws IOException {
		CatalogManager cma = new CatalogManager();
		cma.setIgnoreMissingProperties(true);
		cma.setUseStaticCatalog(false);
		final CatalogResolver cra = new CatalogResolver(cma);
		URL a = getClass().getResource("a/catalog.cat");
		cra.getCatalog().parseCatalog(a);
		InputSource ea = cra.resolveEntity(null,
				"http://www.w3.org/1999/xlink.xsd");
		Assert.assertNotNull(ea);

		CatalogManager cmb = new CatalogManager();
		cmb.setIgnoreMissingProperties(true);
		cmb.setUseStaticCatalog(false);
		final CatalogResolver crb = new CatalogResolver(cmb);
		URL b = getClass().getResource("b/catalog.cat");
		crb.getCatalog().parseCatalog(b);
		InputSource eb = crb.resolveEntity(null,
				"http://www.w3.org/2005/atom-author-link.xsd");
		Assert.assertNotNull(eb);

	}
}
