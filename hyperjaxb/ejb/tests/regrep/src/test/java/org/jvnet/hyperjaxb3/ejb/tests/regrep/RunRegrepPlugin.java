package org.jvnet.hyperjaxb3.ejb.tests.regrep;

import org.jvnet.hyperjaxb3.maven.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunRegrepPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);
		mojo.roundtripTestClassName = "org.freebxml.omar.jaxb.bindings.RoundtripTest";
	}
}
