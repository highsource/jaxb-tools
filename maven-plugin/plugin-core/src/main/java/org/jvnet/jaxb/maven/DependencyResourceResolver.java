package org.jvnet.jaxb.maven;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

public interface DependencyResourceResolver {
    URL resolveDependencyResource(DependencyResource dependencyResource) throws MojoExecutionException;
    List<URI> resolveDependencyResources(DependencyResource dependencyResource) throws MojoExecutionException;
}
