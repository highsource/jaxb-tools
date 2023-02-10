package org.jvnet.jaxb;

import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;

public interface DependencyResourceResolver {
	public URL resolveDependencyResource(DependencyResource dependencyResource)
			throws MojoExecutionException;

}
