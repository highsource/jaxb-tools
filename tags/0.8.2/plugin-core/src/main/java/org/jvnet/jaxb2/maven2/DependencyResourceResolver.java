package org.jvnet.jaxb2.maven2;

import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;

public interface DependencyResourceResolver {
	public URL resolveDependencyResource(DependencyResource dependencyResource)
			throws MojoExecutionException;

}
