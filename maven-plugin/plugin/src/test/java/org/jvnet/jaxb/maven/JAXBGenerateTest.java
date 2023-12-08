/*
 * Copyright [2006] java.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jvnet.jaxb.maven;

import java.io.File;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingResult;

public abstract class JAXBGenerateTest extends AbstractMojoTestCase {

	static {
		System.setProperty("basedir", getBaseDir().getAbsolutePath());
	}

	protected ProjectBuilder mavenProjectBuilder;

	protected void setUp() throws Exception {
		super.setUp();

		mavenProjectBuilder = (ProjectBuilder) getContainer().lookup(
            ProjectBuilder.class);
	}

	protected static File getBaseDir() {
		try {
			return (new File(JAXBGenerateTest.class.getProtectionDomain()
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
		"src/test/resources/test-pom.xml");

        final ArtifactRepository localRepository = new MavenArtifactRepository( "local",
            new File(getBaseDir(), "target/test-repository").toURI().toURL().toString(),
            new DefaultRepositoryLayout(),
            new ArtifactRepositoryPolicy(),
            new ArtifactRepositoryPolicy());

        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setLocalRepository(localRepository);
        final MavenSession mavenSession = new MavenSession(null, null, request, null);

		final ProjectBuildingResult mavenProject = mavenProjectBuilder.build(pom, null);


		final XJCMojo generator = (XJCMojo) lookupMojo("generate", pom);
        generator.setMavenSession(mavenSession);
        generator.setProject(mavenProject.getProject());
		generator.setSchemaDirectory(new File(getBaseDir(),"src/test/resources/"));
		generator.setSchemaIncludes(new String[] { "*.xsd" });
		generator.setBindingIncludes(new String[] { "*.xjb" });
		generator.setGenerateDirectory(new File(getBaseDir(), "target/test/generated-sources"));
		generator.setVerbose(true);
		generator.setGeneratePackage("unittest");
		generator.setRemoveOldOutput(false);

		generator.execute();
	}

	public static void main(String[] args) throws Exception {
		//	new JAXBGenerateTest().testExecute();
	}
}
