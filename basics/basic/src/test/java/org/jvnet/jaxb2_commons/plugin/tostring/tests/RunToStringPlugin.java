package org.jvnet.jaxb2_commons.plugin.tostring.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jvnet.jaxb.maven.AbstractXJC2Mojo;
import org.jvnet.jaxb.maven.test.RunXJC2Mojo;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;

import com.sun.tools.xjc.Options;

public class RunToStringPlugin extends RunXJC2Mojo {

	@Override
	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/test/resources");
	}

	@Override
	protected void configureMojo(AbstractXJC2Mojo<Options> mojo) {
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
