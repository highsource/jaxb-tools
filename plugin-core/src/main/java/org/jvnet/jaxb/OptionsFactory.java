package org.jvnet.jaxb;

import org.apache.maven.plugin.MojoExecutionException;

public interface OptionsFactory<O> {

	public O createOptions(OptionsConfiguration optionsConfiguration)
			throws MojoExecutionException;

}
