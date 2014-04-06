package org.jvnet.jaxb2.maven2.tests.catalog;

import java.io.File;

import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunPlainCatalogMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);

		mojo
				.setCatalog(new File(getBaseDir(),
						"src/main/resources/catalog.cat"));

		mojo.setForceRegenerate(true);
	}

}
