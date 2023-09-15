package org.jvnet.jaxb.maven.tests.rnc;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.reader.Ring;

public class RunRNCMojo extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo mojo) {
		super.configureMojo(mojo);

		// final ResourceEntry a_xsd = new ResourceEntry();
		// a_xsd.setUrl("http://www.ab.org/a.xsd");
		mojo.setStrict(false);
		mojo.setSchemaLanguage("RELAXNG_COMPACT");
		mojo.setSchemaIncludes(new String[] { "*.rnc" });
		mojo.setGeneratePackage("foo");
		// mojo.setSchemas(new ResourceEntry[] { a_xsd });
		// mojo.setCatalog(new File(getBaseDir(),
		// "src/main/resources/catalog.cat"));
		mojo.setForceRegenerate(true);
//		Ring.begin();
	}

}
