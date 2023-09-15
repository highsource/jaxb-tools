package org.jvnet.mjiip.tests.p_o;

import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import com.sun.tools.xjc.Options;

public class RunP_OPlugin extends RunXJCMojo {

	@Override
	protected void configureMojo(AbstractXJCMojo<Options> mojo) {
		super.configureMojo(mojo);
//		mojo.setExtension(true);
//		mojo.setForceRegenerate(true);
	}
}
