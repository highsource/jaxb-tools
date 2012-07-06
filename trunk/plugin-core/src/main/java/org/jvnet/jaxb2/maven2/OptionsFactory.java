package org.jvnet.jaxb2.maven2;

import org.apache.maven.plugin.MojoExecutionException;

public interface OptionsFactory<O> {

	public O createOptions(OptionsConfiguration optionsConfiguration)
			throws MojoExecutionException;

}
