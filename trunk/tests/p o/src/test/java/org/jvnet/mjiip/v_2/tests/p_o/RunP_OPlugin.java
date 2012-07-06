package org.jvnet.mjiip.v_2.tests.p_o;

import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class RunP_OPlugin extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
//		mojo.setExtension(true);
//		mojo.setForceRegenerate(true);
	}
}
