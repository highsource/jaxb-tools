package org.jvnet.jaxb2.maven2.tests.MAVEN_JAXB2_PLUGIN_53.b;

import java.io.File;

import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunPlainCatalogMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);

		mojo.setCatalog(new File(getBaseDir(),
				"src\\main\\resources\\catalog.cat"));
//		mojo.setCatalogResolver(MavenCatalogResolver.class.getName());
		mojo.getArgs().add("C:/Repository1/org/jvnet/jaxb2/maven2/maven-jaxb2-plugin-tests-MAVEN_JAXB2_PLUGIN-53-a/0.8.3-SNAPSHOT/maven-jaxb2-plugin-tests-MAVEN_JAXB2_PLUGIN-53-a-0.8.3-SNAPSHOT.jar");

		mojo.setUseDependenciesAsEpisodes(true);

		mojo.setForceRegenerate(true);
	}

}
