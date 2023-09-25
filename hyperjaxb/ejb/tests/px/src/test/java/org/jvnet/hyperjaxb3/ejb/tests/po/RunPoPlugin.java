package org.jvnet.hyperjaxb3.ejb.tests.po;

import java.io.File;

import org.jvnet.hyperjaxb3.maven.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunPoPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);

		mojo.persistenceXml = new File(getBaseDir(), "src/main/etc/persistence.xml");
	}
}
