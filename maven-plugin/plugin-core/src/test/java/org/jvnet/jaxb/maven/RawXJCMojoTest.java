package org.jvnet.jaxb.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.stubs.ArtifactStub;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.junit.Assert.assertEquals;

public class RawXJCMojoTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File testJarFile;

    @Before
    public void createJarFile() throws Exception {
        testJarFile = temporaryFolder.newFile("test.jar");
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream(testJarFile))) {
            out.putNextEntry(new JarEntry("dir/"));
            out.closeEntry();
            out.putNextEntry(new JarEntry("dir/nested.xjb"));
            out.write("nested binding".getBytes(StandardCharsets.UTF_8));
            out.closeEntry();
            out.putNextEntry(new JarEntry("root.xjb"));
            out.write("root binding".getBytes(StandardCharsets.UTF_8));
            out.closeEntry();
        }
    }

    @Test
    public void collectBindingUrisFromDependencies() throws Exception {
        List<URI> bindings = new ArrayList<>();

        final RawXJCMojo<Void> mojo = new RawXJCMojo<>(XJCVersion.UNDEFINED) {

            @Override
            public MavenProject getProject() {
                MavenProject project = new MavenProject() {
                    @Override
                    public Set<Artifact> getArtifacts() {
                        Set<Artifact> artifacts = new HashSet<>();
                        ArtifactStub stubJar = new ArtifactStub();
                        stubJar.setArtifactId("test");
                        stubJar.setScope("compile");
                        stubJar.setType("jar");
                        stubJar.setFile(testJarFile);
                        artifacts.add(stubJar);

                        ArtifactStub stubProvided = new ArtifactStub();
                        stubProvided.setArtifactId("test-provided");
                        stubProvided.setScope("provided");
                        stubProvided.setType("jar");
                        artifacts.add(stubJar);

                        ArtifactStub stubPom = new ArtifactStub();
                        stubPom.setArtifactId("test-pom");
                        stubPom.setScope("compile");
                        stubPom.setType("pom");
                        artifacts.add(stubPom);
                        return artifacts;
                    }
                };
                return project;
            }

            @Override
            protected IOptionsFactory<Void> getOptionsFactory() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void doExecute(Void options) throws MojoExecutionException {
                throw new UnsupportedOperationException();
            }
        };

        mojo.collectBindingUrisFromDependencies(bindings);

        assertEquals(2, bindings.size());
        assertEquals(URI.create("jar:" + testJarFile.toURI() + "!/dir/nested.xjb"), bindings.get(0));
        assertEquals(URI.create("jar:" + testJarFile.toURI() + "!/root.xjb"), bindings.get(1));
        assertEquals("nested binding", readContent(bindings.get(0)));
        assertEquals("root binding", readContent(bindings.get(1)));
    }

    @Test
    public void collectsBindingUrisFromArtifact() throws Exception {
        List<URI> bindings = new ArrayList<>();

        final RawXJCMojo<Void> mojo = new RawXJCMojo<Void>(XJCVersion.UNDEFINED) {

			@Override
			protected IOptionsFactory<Void> getOptionsFactory() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void doExecute(Void options) throws MojoExecutionException {
				throw new UnsupportedOperationException();
			}
		};

        mojo.collectBindingUrisFromArtifact(testJarFile, bindings);

        assertEquals(2, bindings.size());
        assertEquals(URI.create("jar:" + testJarFile.toURI() + "!/dir/nested.xjb"), bindings.get(0));
        assertEquals(URI.create("jar:" + testJarFile.toURI() + "!/root.xjb"), bindings.get(1));
        assertEquals("nested binding", readContent(bindings.get(0)));
        assertEquals("root binding", readContent(bindings.get(1)));
    }

    private String readContent(URI uri) throws Exception {
        try (InputStream in = uri.toURL().openConnection().getInputStream()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return out.toString(StandardCharsets.UTF_8.name());
        }
    }
}
