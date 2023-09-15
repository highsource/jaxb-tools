package org.jvnet.jaxb.maven.tests.res;

import java.io.File;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.DependencyResource;
import org.jvnet.jaxb.maven.ResourceEntry;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunResMojo extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);

		mojo.setCatalog(new File(getBaseDir(),"src/main/jaxb/catalog.cat"));
		mojo.setExtension(true);
		final ResourceEntry purchaseorder_xsd = new ResourceEntry();
		final DependencyResource purchaseorder_xsd_dependencyResource = new DependencyResource();
		purchaseorder_xsd.setDependencyResource(purchaseorder_xsd_dependencyResource);
		purchaseorder_xsd.getDependencyResource().setGroupId("org.jvnet.jaxb");
		purchaseorder_xsd.getDependencyResource().setArtifactId("jaxb-maven-plugin-tests-po-2.3");
		purchaseorder_xsd.getDependencyResource().setResource("purchaseorder.xsd");
		mojo.setSchemas(new ResourceEntry[]{
				purchaseorder_xsd

		});
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
