package org.jvnet.jaxb.maven.tests.MAVEN_JAXB2_PLUGIN_77;

import java.io.File;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunMAVEN_JAXB2_PLUGIN_77Mojo extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);

		mojo.setSchemaDirectory(new File(getBaseDir(), "src/main/resources/META-INF/project/schemas"));
		mojo.setGeneratePackage("com.company.project.service.types");
		mojo.setCatalog(new File(getBaseDir(),"src/main/jaxb/catalog.cat"));
		mojo.setCatalogResolver("org.jvnet.jaxb.maven.resolver.tools.ClasspathCatalogResolver");
		mojo.setExtension(true);
//		mojo.
//
//		final ResourceEntry a_xsd = new ResourceEntry();
//		a_xsd.setUrl("http://www.ab.org/a.xsd");
//		mojo.setStrict(false);
//		mojo.setSchemaIncludes(new String[] {});
//		mojo.setSchemas(new ResourceEntry[] { a_xsd });
//		mojo.setCatalog(new File(getBaseDir(), "src/main/resources/catalog.cat"));
		mojo.setForceRegenerate(true);
	}

}
