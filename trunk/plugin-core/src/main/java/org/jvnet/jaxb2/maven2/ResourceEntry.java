package org.jvnet.jaxb2.maven2;

import org.apache.maven.model.FileSet;

public class ResourceEntry {

	private FileSet fileset;

	public FileSet getFileset() {
		return fileset;
	}

	public void setFileset(FileSet fileset) {
		this.fileset = fileset;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private DependencyResource dependencyResource;

	public DependencyResource getDependencyResource() {
		return dependencyResource;
	}

	public void setDependencyResource(DependencyResource dependencyResource) {
		this.dependencyResource = dependencyResource;
	}
}
