package org.jvnet.hyperjaxb3.maven2.tests;

import java.io.File;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;
import org.sonatype.plexus.build.incremental.DefaultBuildContext;

public class Hyperjaxb3MojoTest extends RunXJC2Mojo {

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
		final MavenProject project = new MavenProject();
		project.setFile(getSchemaDirectory());
		mojo.setProject(project);
		mojo.setDebug(true);
		mojo.setForceRegenerate(true);
		mojo.setBuildContext(new DefaultBuildContext());
//		mojo.setLog(new SystemStreamLog());
	}

	@Override
	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/test/resources");
	}
}
