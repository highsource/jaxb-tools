package org.jvnet.jaxb.plugin.tostring.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunToStringPlugin extends RunXJCMojo {

	@Override
	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/test/resources");
	}

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);
		mojo.setForceRegenerate(true);
	}

	@Override
	public List<String> getArgs() {
		final List<String> args = new ArrayList<>(super.getArgs());
		args.add("-XtoString");
		args.add("-XtoString-toStringStrategy="
				+ JAXBToStringStrategy.class.getName());
		return args;
	}

}
