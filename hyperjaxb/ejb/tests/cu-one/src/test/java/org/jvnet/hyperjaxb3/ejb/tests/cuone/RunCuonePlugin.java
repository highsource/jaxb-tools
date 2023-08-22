package org.jvnet.hyperjaxb3.ejb.tests.cuone;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunCuonePlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
//		mojo.result = "mappingFiles";
		super.configureHyperjaxb3Mojo(mojo);
		mojo.getArgs().add("-Xannotate");
	}

}
