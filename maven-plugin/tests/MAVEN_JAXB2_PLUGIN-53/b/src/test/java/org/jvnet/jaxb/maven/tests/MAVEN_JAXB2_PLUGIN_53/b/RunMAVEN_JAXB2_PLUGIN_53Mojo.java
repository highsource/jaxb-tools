package org.jvnet.jaxb.maven.tests.MAVEN_JAXB2_PLUGIN_53.b;

import java.io.File;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunMAVEN_JAXB2_PLUGIN_53Mojo extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);
		mojo.setCatalog(new File(getBaseDir(), "src/main/resources/catalog.cat"));
		mojo.setUseDependenciesAsEpisodes(true);

		mojo.setForceRegenerate(true);
	}

}
