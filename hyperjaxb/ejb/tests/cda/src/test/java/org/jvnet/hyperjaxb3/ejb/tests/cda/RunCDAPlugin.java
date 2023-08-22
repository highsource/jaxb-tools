package org.jvnet.hyperjaxb3.ejb.tests.cda;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunCDAPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);
		mojo.setSchemaIncludes(new String[]{"CDASchemas/cda/Schemas/CDA.xsd"});
	}

}
