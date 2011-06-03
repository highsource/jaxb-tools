package org.jvnet.jaxb2.maven2;

import org.apache.maven.model.Dependency;

public class DependencyResource extends Dependency {

	private String resource;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String toString() {
		return "Dependency {groupId=" + getGroupId() + ", artifactId="
				+ getArtifactId() + ", version=" + getVersion() + ", type="
				+ getType() + ", resource=" + getResource() + "}";
	}

}
