package org.jvnet.jaxb.maven.tests.rnc;

import org.jvnet.jaxb.maven.AbstractXJC2Mojo;
import org.jvnet.jaxb.maven.test.RunXJC2Mojo;

import com.sun.tools.xjc.reader.Ring;

public class RunRNCMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
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
