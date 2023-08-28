package org.jvnet.jaxb.maven.tests.catalog;

import java.io.File;

import org.jvnet.jaxb.maven.AbstractXJC2Mojo;
import org.jvnet.jaxb.maven.resolver.tools.ClasspathCatalogResolver;
import org.jvnet.jaxb.maven.test.RunXJC2Mojo;

import com.sun.tools.xjc.Options;

public class RunCatalogMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo<Options> mojo) {
		super.configureMojo(mojo);

		mojo
				.setCatalog(new File(getBaseDir(),
						"src/main/resources/catalog.cat"));
		mojo.setCatalogResolver(ClasspathCatalogResolver.class.getName());

		mojo.setForceRegenerate(true);
	}

}
