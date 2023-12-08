package org.jvnet.hyperjaxb3.ejb.tests.episodes.b.tests;

import java.io.File;
import java.util.Map;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.settings.MavenSettingsBuilder;
import org.apache.maven.settings.Settings;
import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;

public class RunEpisodesBPlugin extends AbstractMojoTestCase {

	static {
		System.setProperty("basedir", getBaseDir().getAbsolutePath());
	}

	protected MavenProjectBuilder mavenProjectBuilder;

	protected Hyperjaxb3Mojo mojo;

	protected ArtifactRepository localRepository;

	protected void setUp() throws Exception {
		super.setUp();

		mavenProjectBuilder = (MavenProjectBuilder) getContainer().lookup(
				MavenProjectBuilder.ROLE);
		ArtifactFactory artifactFactory = (ArtifactFactory) getContainer()
				.lookup(ArtifactFactory.ROLE);

		final Map<String, Mojo> mojos = (Map<String, Mojo>) getContainer()
				.lookupMap(Mojo.ROLE);

		for (Mojo mojo : mojos.values()) {
			if (mojo instanceof Hyperjaxb3Mojo) {
				this.mojo = (Hyperjaxb3Mojo) mojo;
			}
		}

		MavenSettingsBuilder settingsBuilder = (MavenSettingsBuilder) getContainer()
				.lookup(MavenSettingsBuilder.ROLE);
		ArtifactRepositoryLayout repositoryLayout = (ArtifactRepositoryLayout) getContainer()
				.lookup(ArtifactRepositoryLayout.ROLE, "default");

		Settings settings = settingsBuilder.buildSettings();

		String url = settings.getLocalRepository();

		if (!url.startsWith("file:")) {
			url = "file://" + url;
		}

		localRepository = new DefaultArtifactRepository("local", url,
				new DefaultRepositoryLayout());
	}

	protected static File getBaseDir() {
		try {
			return (new File(RunEpisodesBPlugin.class.getProtectionDomain()
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

		final File pom = new File(getBaseDir(), "pom.xml");

		final MavenProject mavenProject = mavenProjectBuilder.build(pom,
				localRepository, null);

		mojo.setSchemaDirectory(new File(getBaseDir(), "src/main/resources/"));
		mojo.setSchemaIncludes(new String[] { "*.xsd" });
		mojo.setBindingIncludes(new String[] { "*.xjb" });
		mojo.setGenerateDirectory(new File(getBaseDir(),
				"target/generated-sources/xjc-test"));
		mojo.setVerbose(true);
		mojo.setRemoveOldOutput(true);

		mojo.setProject(mavenProject);
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setLocalRepository(localRepository);
        final MavenSession mavenSession = new MavenSession(null, null, request, null);
		mojo.setMavenSession(mavenSession);
		mojo.setExtension(true);
		mojo.setDebug(false);
		// mojo.setSchemaLanguage("XMLSCHEMA");
		mojo.roundtripTestClassName = getClass().getPackage().getName()
				+ ".RoundtripTest";
		mojo.setForceRegenerate(true);

		final Dependency episode = new Dependency();

		episode.setGroupId("org.jvnet.hyperjaxb3");
		episode.setArtifactId("hyperjaxb3-ejb-tests-episodes-a");
		episode.setVersion("0.5.5-SNAPSHOT");
		mojo.setEpisodes(new Dependency[] { episode });

		mojo.execute();
	}

}
