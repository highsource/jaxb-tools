package org.jvnet.hyperjaxb3.maven2.ejb.test;

import org.apache.maven.project.MavenProject;
import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunEjbHyperjaxb3Mojo extends RunXJC2Mojo {

	@Override
	protected AbstractXJC2Mojo createMojo() {
		return new Hyperjaxb3Mojo();
	}

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
		configureHyperjaxb3Mojo((Hyperjaxb3Mojo) mojo);

	}

	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		mojo.setProject(new MavenProject());
		mojo.setExtension(true);
		mojo.setDebug(true);
		// mojo.setBvariant = "ejb";
		mojo.roundtripTestClassName = getClass().getPackage().getName()
				+ ".RoundtripTest";
		mojo.setForceRegenerate(true);
	}

}
