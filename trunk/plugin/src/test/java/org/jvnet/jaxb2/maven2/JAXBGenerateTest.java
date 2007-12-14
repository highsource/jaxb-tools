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

package org.jvnet.jaxb2.maven2;

import java.io.File;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;

public class JAXBGenerateTest extends AbstractMojoTestCase {

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
		
        final ArtifactRepository localRepository = new DefaultArtifactRepository( "local", 
        		
        		new File(getBaseDir(), "target/test-repository").toURI().toURL().toString()        		, new DefaultRepositoryLayout());
		
		
		final MavenProject mavenProject = mavenProjectBuilder.build(pom, localRepository, null);
		

		final XJC2Mojo generator = (XJC2Mojo) lookupMojo("generate", pom);
		generator.setProject(mavenProject);
		generator.setLocalRepository(localRepository);
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
		new JAXBGenerateTest().testExecute();
	}
}
