package org.jvnet.jaxb2_commons.plugin.annotate.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.project.MavenProject;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunPluginTest extends RunXJC2Mojo {

	@Override
	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/test/resources");
	}

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
		mojo.setProject(new MavenProject());
		mojo.setForceRegenerate(true);
		mojo.setExtension(true);

	}

	@Override
	public List<String> getArgs() {
		final List<String> args = new ArrayList<String>(super.getArgs());
		args.add("-Xannotate");
		return args;
	}
}
