package org.jvnet.jaxb2.maven2;

import org.apache.maven.artifact.factory.ArtifactFactory;

/**
 * @author Kohsuke Kawaguchi
 */
public class Artifact {
    private String groupId;
    private String artifactId;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public org.apache.maven.artifact.Artifact toArtifact(ArtifactFactory factory) {
        return factory.createArtifact(groupId,artifactId,version,null,"jar");
    }
}
