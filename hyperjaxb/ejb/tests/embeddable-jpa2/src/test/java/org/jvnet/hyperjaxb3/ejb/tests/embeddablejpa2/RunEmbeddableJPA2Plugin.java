package org.jvnet.hyperjaxb3.ejb.tests.embeddablejpa2;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunEmbeddableJPA2Plugin extends RunEjbHyperjaxb3Mojo {
	
	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		// TODO Auto-generated method stub
		super.configureHyperjaxb3Mojo(mojo);
		mojo.getArgs().add("-Xannotate");
		mojo.variant = "jpa2";
	}

}
