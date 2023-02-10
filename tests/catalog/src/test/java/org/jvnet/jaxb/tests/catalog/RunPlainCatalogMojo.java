package org.jvnet.jaxb.tests.catalog;

import java.io.File;

import org.jvnet.jaxb.AbstractXJC2Mojo;
import org.jvnet.jaxb.test.RunXJC2Mojo;

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
