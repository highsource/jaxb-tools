package org.jvnet.jaxb2.maven2.tests.res;

import java.io.File;

import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.DependencyResource;
import org.jvnet.jaxb2.maven2.ResourceEntry;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunResMojo extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
		
		mojo.setCatalog(new File(getBaseDir(),"src/main/jaxb/catalog.cat"));
		mojo.setExtension(true);
		final ResourceEntry purchaseorder_xsd = new ResourceEntry();
		final DependencyResource purchaseorder_xsd_dependencyResource = new DependencyResource();
		purchaseorder_xsd.setDependencyResource(purchaseorder_xsd_dependencyResource);
		purchaseorder_xsd.getDependencyResource().setGroupId("org.jvnet.jaxb2.maven2");
		purchaseorder_xsd.getDependencyResource().setArtifactId("maven-jaxb2-plugin-tests-po-2.3");
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
