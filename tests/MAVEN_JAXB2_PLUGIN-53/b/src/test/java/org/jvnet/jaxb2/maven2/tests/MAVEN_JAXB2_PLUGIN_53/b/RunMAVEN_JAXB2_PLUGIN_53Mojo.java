package org.jvnet.jaxb2.maven2.tests.MAVEN_JAXB2_PLUGIN_53.b;

import java.io.File;

import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunMAVEN_JAXB2_PLUGIN_53Mojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
		mojo.setCatalog(new File(getBaseDir(), "src/main/resources/catalog.cat"));
		mojo.setUseDependenciesAsEpisodes(true);

		mojo.setForceRegenerate(true);
	}

}
