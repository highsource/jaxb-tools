package org.jvnet.jaxb.maven.tests.catalog;

import java.io.File;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.resolver.tools.ClasspathCatalogResolver;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunCatalogMojo extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);

		mojo
				.setCatalog(new File(getBaseDir(),
						"src/main/resources/catalog.cat"));
		mojo.setCatalogResolver(ClasspathCatalogResolver.class.getName());

		mojo.setForceRegenerate(true);
	}

}
