package org.jvnet.jaxb.maven;

import org.apache.maven.plugin.MojoExecutionException;

public interface IOptionsFactory<O> {

	public O createOptions(OptionsConfiguration optionsConfiguration)
			throws MojoExecutionException;

}
