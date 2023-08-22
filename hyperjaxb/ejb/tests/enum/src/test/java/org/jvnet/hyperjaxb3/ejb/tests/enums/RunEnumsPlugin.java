package org.jvnet.hyperjaxb3.ejb.tests.enums;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunEnumsPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);
		mojo.setVerbose(true);
	}
}
