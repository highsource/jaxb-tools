package org.jvnet.hyperjaxb3.maven.ejb.test;

import org.apache.maven.project.MavenProject;
import org.jvnet.hyperjaxb3.maven.Hyperjaxb3Mojo;
import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

public class RunEjbHyperjaxb3Mojo extends RunXJCMojo {

	@Override
	protected AbstractXJCMojo createMojo() {
		return new Hyperjaxb3Mojo();
	}

	@Override
	protected void configureMojo(AbstractXJCMojo mojo) {
		super.configureMojo(mojo);
		configureHyperjaxb3Mojo((Hyperjaxb3Mojo) mojo);

	}

	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		final MavenProject project = new MavenProject();
		mojo.setProject(project);
		mojo.setExtension(true);
		mojo.setDebug(true);
		// mojo.setBvariant = "ejb";
		mojo.roundtripTestClassName = getClass().getPackage().getName()
				+ ".RoundtripTest";
		mojo.setForceRegenerate(true);
	}

}
