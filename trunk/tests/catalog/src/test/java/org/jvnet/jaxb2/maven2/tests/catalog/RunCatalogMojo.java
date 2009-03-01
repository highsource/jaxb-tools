package org.jvnet.jaxb2.maven2.tests.catalog;

import java.io.File;

import org.jvnet.jaxb2.maven2.resolver.tools.ClasspathCatalogResolver;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;

public class RunCatalogMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);

		mojo
				.setCatalog(new File(getBaseDir(),
						"src/main/resources/catalog.cat"));
		mojo.setCatalogResolver(ClasspathCatalogResolver.class.getName());

		mojo.setForceRegenerate(true);
	}

}
