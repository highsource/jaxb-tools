package org.jvnet.jaxb.tests.JAXB_1044;

import java.io.File;

import org.jvnet.jaxb.AbstractXJC2Mojo;
import org.jvnet.jaxb.ResourceEntry;
import org.jvnet.jaxb.test.RunXJC2Mojo;

public class RunJAXB_1044Mojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);

		final ResourceEntry a_xsd = new ResourceEntry();
		a_xsd.setUrl("http://www.ab.org/a.xsd");
		mojo.setStrict(false);
		mojo.setSchemaIncludes(new String[] {});
		mojo.setSchemas(new ResourceEntry[] { a_xsd });
		mojo.setCatalog(new File(getBaseDir(), "src/main/resources/catalog.cat"));
		mojo.setForceRegenerate(true);
	}

}
