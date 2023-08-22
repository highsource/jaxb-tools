package org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunIssuesJPA2Plugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);
		mojo.setVerbose(true);
		mojo.variant = "jpa2";
		mojo.roundtripTestClassName = null;
		mojo.setDebug(false);
	}
}
