package org.jvnet.mjiip.v_2;

import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoPhase;
import org.jvnet.mjiip.v_2_2.XJC22Mojo;

import com.sun.tools.xjc.Options;

/**
 * JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@MojoGoal("generate")
@MojoPhase("generate-sources")
public class XJC2Mojo extends XJC22Mojo {

	private final org.jvnet.jaxb2.maven2.OptionsFactory<Options> optionsFactory = new OptionsFactory();

	@Override
	protected org.jvnet.jaxb2.maven2.OptionsFactory<Options> getOptionsFactory() {
		return optionsFactory;
	}
}
