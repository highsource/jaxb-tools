package org.jvnet.mjiip.tests.p_o;

import org.jvnet.jaxb.maven.AbstractXJC2Mojo;
import org.jvnet.jaxb.maven.test.RunXJC2Mojo;

public class RunP_OPlugin extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
//		mojo.setExtension(true);
//		mojo.setForceRegenerate(true);
	}
}
