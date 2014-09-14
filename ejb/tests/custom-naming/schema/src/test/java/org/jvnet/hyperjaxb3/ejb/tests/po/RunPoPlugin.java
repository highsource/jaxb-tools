package org.jvnet.hyperjaxb3.ejb.tests.po;

import java.io.File;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;

public class RunPoPlugin extends AbstractMojoTestCase {
	
	static {
		System.setProperty("basedir", getBaseDir().getAbsolutePath());
	}

	protected MavenProjectBuilder mavenProjectBuilder;

	protected void setUp() throws Exception {
		super.setUp();

		mavenProjectBuilder = (MavenProjectBuilder) getContainer().lookup(
				MavenProjectBuilder.ROLE);
	}

	protected static File getBaseDir() {
		try {
			return (new File(RunPoPlugin.class.getProtectionDomain()
					.getCodeSource().getLocation().getFile())).getParentFile()
					.getParentFile().getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}
	
	/**
	 * Validate the generation of a java files from purchaseorder.xsd.
	 * 
	 * @throws MojoExecutionException
	 */
	public void testExecute() throws Exception {

		final File pom = new File(getBaseDir(),
				"pom.xml");

		final ArtifactRepository localRepository = new DefaultArtifactRepository(
				"local",

				new File(getBaseDir(), "target/test-repository").toURI()
						.toURL().toString(), new DefaultRepositoryLayout());

		final MavenProject mavenProject = mavenProjectBuilder.build(pom,
				localRepository, null);

		final Hyperjaxb3Mojo generator = (Hyperjaxb3Mojo) lookupMojo(
				"org.jvnet.hyperjaxb3",
				"maven-hyperjaxb3-plugin",
				"0.5.4-SNAPSHOT",
				"generate", null);
		generator.setProject(mavenProject);
		generator.setLocalRepository(localRepository);
		generator.setSchemaDirectory(new File(getBaseDir(),
				"src/main/resources"));
		generator.setSchemaIncludes(new String[] { "*.xsd" });
		generator.setBindingIncludes(new String[] { "*.xjb" });
		generator.setGenerateDirectory(new File(getBaseDir(),
				"target/test/generated-sources"));
		generator.setVerbose(true);
		generator.setRemoveOldOutput(true);
		generator.execute();
	}
}
