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
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;

/**
 * A mojo that uses JAXB 1.x XJC compiler to generate java source classes from
 * schemas (XML schemas, DTD, WSDL, or RELAXNG). For details on JAXB see <a
 * href="https://jaxb.dev.java.net/1.0/">JAXB 1.x Project</a>.
 * 
 * @goal generate
 * @phase generate-sources
 * 
 * @description JAXB-1.x Generator Plugin for Maven-2, bound by default at
 *              'generate-sources' phase.
 * @author Kostis Anagnostopoulos (ankostis@mail.com)
 */
public class XJC2Mojo extends AbstractXJC2Mojo {

	/**
	 * <p>
	 * Type of input schema language. One of: DTD, XMLSCHEMA, RELAXNG,
	 * RELAXNG_COMPACT, WSDL, AUTODETECT
	 * </p>
	 * <p>
	 * If unspecified, it is assumed AUTODETECT.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.schemaLanguage}"
	 * 
	 */
	protected String schemaLanguage;

	/**
	 * The source directory containing *.xsd schema files. Notice that binding
	 * files are searched by default in this deriectory.
	 * 
	 * @parameter default-value="src/main/resources"
	 *            expression="${maven.xjc2.schemaDirectory}"
	 * @required
	 */
	protected File schemaDirectory;

	/**
	 * <p>
	 * A list of regular expression file search patterns to specify the schemas
	 * to be processed. Searching is based from the root of
	 * <code>schemaDirectory</code>.
	 * </p>
	 * <p>
	 * If left udefined, then all *.xsd files in schemaDirectory will be
	 * processed.
	 * </p>
	 * 
	 * @parameter
	 */
	protected String[] schemaIncludes = new String[] { "*.xsd" };

	/**
	 * A list of regular expression file search patterns to specify the schemas
	 * to be excluded from the <code>schemaIncludes</code> list. Searching is
	 * based from the root of schemaDirectory.
	 * 
	 * @parameter
	 */
	protected String[] schemasExcludes;

	/**
	 * <p>
	 * The source directory containing the *.xjb binding files.
	 * </p>
	 * <p>
	 * If left undefined, then the <code>schemaDirectory</code> is assumed.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.bindingDirectory}"
	 */
	protected File bindingDirectory;

	/**
	 * <p>
	 * A list of regular expression file search patterns to specify the binding
	 * files to be processed. Searching is based from the root of
	 * <code>bindingDirectory</code>.
	 * </p>
	 * <p>
	 * If left undefined, then all *.xjb files in schemaDirectory will be
	 * processed.
	 * </p>
	 * 
	 * @parameter
	 */
	protected String[] bindingIncludes = new String[] { "*.xjb" };

	/**
	 * A list of regular expression file search patterns to specify the binding
	 * files to be excluded from the <code>bindingIncludes</code>. Searching
	 * is based from the root of bindingDirectory.
	 * 
	 * @parameter
	 */
	protected String[] bindingExcludes;

	/**
	 * If 'true', maven's default exludes are NOT added to all the excludes
	 * lists.
	 * 
	 * @parameter default-value="false"
	 *            expression="${maven.xjc2.disableDefaultExcludes}"
	 */
	protected boolean disableDefaultExcludes;

	/**
	 * <p>
	 * Specify the catalog file to resolve external entity references (xjc's
	 * -catalog option)
	 * </p>
	 * <p>
	 * Support TR9401, XCatalog, and OASIS XML Catalog format. See the
	 * catalog-resolver sample and this article for details.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.catalog}"
	 */
	protected File catalog;

	/**
	 * <p>
	 * The generated classes will all be placed under this Java package (xjc's
	 * -p option), unless otherwise specified in the schemas.
	 * </p>
	 * <p>
	 * If left unspecified, the package will be derived from the schemas only.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.generatePackage}"
	 */
	protected String generatePackage;

	/**
	 * <p>
	 * Generated code will be written under this directory.
	 * </p>
	 * <p>
	 * For instance, if you specify <code>generateDirectory="doe/ray"</code>
	 * and <code>generatePackage="org.here"</code>, then files are generated
	 * to <code>doe/ray/org/here</code>.
	 * </p>
	 * 
	 * @parameter default-value="${project.build.directory}/generated-sources/xjc"
	 *            expression="${maven.xjc2.generateDirectory}"
	 * @required
	 */
	protected File generateDirectory;

	/**
	 * If 'true', the generated Java source files are set as read-only (xjc's
	 * -readOnly option).
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.readOnly}"
	 */
	protected boolean readOnly;

	/**
	 * If 'true', the XJC binding compiler will run in the extension mode (xjc's
	 * -extension option). Otherwise, it will run in the strict conformance
	 * mode.
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.extension}"
	 */
	protected boolean extension;

	/**
	 * If 'true', Perform strict validation of the input schema (xjc's -nv
	 * option).
	 * 
	 * @parameter default-value="true" expression="${maven.xjc2.strict}"
	 */
	protected boolean strict;

	/**
	 * If 'false', the plugin will not write the generated code to disk.
	 * 
	 * @parameter default-value="true" expression="${maven.xjc2.writeCode}"
	 */
	protected boolean writeCode = true;

	// /////////////////////////////////////////////
	// NO, set java VM proxy properties into .m2/settings.xml instead.
	// /////////////////////////////////////////////
	// /**
	// * Set VM's HTTP/HTTPS proxy properties.
	// *
	// * @parameter
	// */
	// protected String proxyHost;
	//
	// /**
	// * Set VM's HTTP/HTTPS proxy properties.
	// *
	// * @parameter
	// */
	// protected String proxyPort;
	// /////////////////////////////////////////////

	/**
	 * <p>
	 * If 'true', the plugin and the XJC compiler are both set to verbose mode
	 * (xjc's -verbose option).
	 * </p>
	 * <p>
	 * It is automatically set to 'true' when maven is run in debug mode (mvn's
	 * -X option).
	 * </p>
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.verbose}"
	 */
	protected boolean verbose;

	/**
	 * <p>
	 * If 'true', the XJC compiler is set to debug mode (xjc's -debug option)and
	 * the 'com.sun.tools.xjc.Options.findServices' property is set, to print
	 * any add-on instanciation messages.
	 * </p>
	 * <p>
	 * It is automatically set to 'true' when maven is run in debug mode (mvn's
	 * -X option).
	 * </p>
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.debug}"
	 */
	protected boolean debug;

	/**
	 * <p>
	 * A list of extra XJC's command-line arguments (items must include the dash
	 * '-').
	 * </p>
	 * <p>
	 * Arguments set here take precedence over other mojo parameters.
	 * </p>
	 * 
	 * @parameter
	 */
	protected List args;

	/**
	 * If 'true', no up-to-date check is performed and the XJC always
	 * re-generates the sources.
	 * 
	 * @parameter default-value="false"
	 *            expression="${maven.xjc2.forceRegenerate}"
	 */
	protected boolean forceRegenerate;

	/**
	 * <p>
	 * If 'true', the [generateDirectory] dir will be deleted before the XJC
	 * binding compiler recompiles the source files.
	 * </p>
	 * <p>
	 * Note that if set to 'false', the up-to-date check might not work, since
	 * XJC does not regenerate all files (i.e. files for "any" elements under
	 * 'xjc/org/w3/_2001/xmlschema' directory).
	 * </p>
	 * 
	 * @parameter default-value="true"
	 *            expression="${maven.xjc2.removeOldOutput}"
	 */
	protected boolean removeOldOutput;

	/**
	 * A list of of input files or URLs to consider during the up-to-date. By
	 * default it always considers: 1. schema files, 2. binding files, 3.
	 * catalog file, and 4. the pom.xml file of the project executing this
	 * plugin.
	 * 
	 * @parameter
	 */
	protected String[] otherDepends;

	/**
	 * Project classpath. Used internally when runing the XJC compiler.
	 * 
	 * @parameter expression="${project.compileClasspathElements}"
	 * @required
	 * @readonly
	 */
	protected List classpathElements;

	/**
	 * The Maven project reference, used internally.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * XJC plugins to be made available to XJC. They still need to be activated
	 * by using &lt;args> and enable plugin activation option.
	 * 
	 * @parameter
	 */
	protected Artifact[] plugins;

	/**
	 * Used internally to resolve {@link #plugins} to their jar files.
	 * 
	 * @component
	 */
	protected ArtifactResolver artifactResolver;

	/**
	 * @component
	 */
	protected ArtifactFactory artifactFactory;

	/**
	 * @parameter expression="${localRepository}"
	 * @required
	 */
	protected ArtifactRepository localRepository;

	/**
	 * Artifact factory, needed to download source jars.
	 * 
	 * @component role="org.apache.maven.project.MavenProjectBuilder"
	 * @required
	 * @readonly
	 */
	protected MavenProjectBuilder mavenProjectBuilder;

	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 * 
	 * @throws MojoExecutionException
	 */
	public void execute() throws MojoExecutionException {
		executeImp();
	}

	public void setSchemaLanguage(String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public void setSchemaDirectory(File schemaDirectory) {
		this.schemaDirectory = schemaDirectory;
	}

	public File getSchemaDirectory() {
		return schemaDirectory;
	}

	public void setSchemaIncludes(String[] schemaIncludes) {
		this.schemaIncludes = schemaIncludes;
	}

	public String[] getSchemaIncludes() {
		return schemaIncludes;
	}

	public void setSchemasExcludes(String[] schemasExcludes) {
		this.schemasExcludes = schemasExcludes;
	}

	public String[] getSchemasExcludes() {
		return schemasExcludes;
	}

	public void setBindingDirectory(File bindingDirectory) {
		this.bindingDirectory = bindingDirectory;
	}

	public File getBindingDirectory() {
		return bindingDirectory;
	}

	public void setBindingIncludes(String[] bindingIncludes) {
		this.bindingIncludes = bindingIncludes;
	}

	public String[] getBindingIncludes() {
		return bindingIncludes;
	}

	public void setBindingExcludes(String[] bindingExcludes) {
		this.bindingExcludes = bindingExcludes;
	}

	public String[] getBindingExcludes() {
		return bindingExcludes;
	}

	public void setDisableDefaultExcludes(boolean disableDefaultExcludes) {
		this.disableDefaultExcludes = disableDefaultExcludes;
	}

	public boolean isDisableDefaultExcludes() {
		return disableDefaultExcludes;
	}

	public void setCatalog(File catalog) {
		this.catalog = catalog;
	}

	public File getCatalog() {
		return catalog;
	}

	public void setGeneratePackage(String generatePackage) {
		this.generatePackage = generatePackage;
	}

	public String getGeneratePackage() {
		return generatePackage;
	}

	public void setGenerateDirectory(File generateDirectory) {
		this.generateDirectory = generateDirectory;
	}

	public File getGenerateDirectory() {
		return generateDirectory;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	public boolean isExtension() {
		return extension;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public boolean isStrict() {
		return strict;
	}

	public boolean isWriteCode() {
		return writeCode;
	}

	public void setWriteCode(boolean writeCode) {
		this.writeCode = writeCode;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setArgs(List args) {
		this.args = args;
	}

	public List getArgs() {
		return args;
	}

	public void setForceRegenerate(boolean forceRegenerate) {
		this.forceRegenerate = forceRegenerate;
	}

	public boolean isForceRegenerate() {
		return forceRegenerate;
	}

	public void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	public boolean isRemoveOldOutput() {
		return removeOldOutput;
	}

	public void setOtherDepends(String[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	public String[] getOtherDepends() {
		return otherDepends;
	}

	protected void setClasspathElements(List classpathElements) {
		this.classpathElements = classpathElements;
	}

	public List getClasspathElements() {
		return classpathElements;
	}

	protected void setProject(MavenProject project) {
		this.project = project;
	}

	public MavenProject getProject() {
		return project;
	}

	public Set getPluginURLs() throws MojoExecutionException {

		Set urls = new HashSet();
		if (plugins != null) {
			for (int i = 0; i < plugins.length; i++) {
				org.apache.maven.artifact.Artifact a = plugins[i]
						.toArtifact(artifactFactory);
				try {
					artifactResolver.resolve(a, project
							.getRemoteArtifactRepositories(), localRepository);
					urls.add(a.getFile().toURL());
					final Set a1 = resolveArtifactDependencies(a);
					for (Iterator iterator = a1.iterator(); iterator.hasNext();) {
						org.apache.maven.artifact.Artifact a2 = (org.apache.maven.artifact.Artifact) iterator
								.next();
						artifactResolver.resolve(a2, project
								.getRemoteArtifactRepositories(),
								localRepository);

						urls.add(a2.getFile().toURL());
					}

				} catch (ArtifactResolutionException e) {
					throw new MojoExecutionException(
							"Error attempting to download the plugin: "
									+ plugins[i], e);
				} catch (ArtifactNotFoundException e) {
					throw new MojoExecutionException("Plugin doesn't exist: "
							+ plugins[i], e);
				} catch (MalformedURLException e) {
					throw new MojoExecutionException(
							"Failed to obtain a plugin URL", e);
				} catch (ProjectBuildingException e) {
					throw new MojoExecutionException(
							"Error processing the plugin dependency POM.", e);
				} catch (InvalidDependencyVersionException e) {
					throw new MojoExecutionException(
							"Invalid plugin dependency version.", e);
				}
			}
		}
		return urls;
	}

	/**
	 * This method resolves the dependency artifacts from the project.
	 * 
	 * @param theProject
	 *            The POM.
	 * @return resolved set of dependency artifacts.
	 * 
	 * @throws ArtifactResolutionException
	 * @throws ArtifactNotFoundException
	 * @throws InvalidDependencyVersionException
	 */
	protected Set resolveDependencyArtifacts(MavenProject theProject)
			throws ArtifactResolutionException, ArtifactNotFoundException,
			InvalidDependencyVersionException {
		Set artifacts = theProject.createArtifacts(this.artifactFactory,
				org.apache.maven.artifact.Artifact.SCOPE_RUNTIME,
				new ScopeArtifactFilter(
						org.apache.maven.artifact.Artifact.SCOPE_RUNTIME));

		for (Iterator i = artifacts.iterator(); i.hasNext();) {
			org.apache.maven.artifact.Artifact artifact = (org.apache.maven.artifact.Artifact) i
					.next();
			// resolve the new artifact
			this.artifactResolver.resolve(artifact, project
					.getRemoteArtifactRepositories(), this.localRepository);
		}
		return artifacts;
	}

	/**
	 * This method resolves all transitive dependencies of an artifact.
	 * 
	 * @param artifact
	 *            the artifact used to retrieve dependencies
	 * 
	 * @return resolved set of dependencies
	 * 
	 * @throws ArtifactResolutionException
	 * @throws ArtifactNotFoundException
	 * @throws ProjectBuildingException
	 * @throws InvalidDependencyVersionException
	 */
	protected Set resolveArtifactDependencies(
			org.apache.maven.artifact.Artifact artifact)
			throws ArtifactResolutionException, ArtifactNotFoundException,
			ProjectBuildingException, InvalidDependencyVersionException {
		final org.apache.maven.artifact.Artifact pomArtifact = this.artifactFactory
				.createArtifact(artifact.getGroupId(),
						artifact.getArtifactId(), artifact.getVersion(), "",
						"pom");

		final MavenProject pomProject = mavenProjectBuilder
				.buildFromRepository(pomArtifact, project
						.getRemoteArtifactRepositories(), this.localRepository);

		return resolveDependencyArtifacts(pomProject);
	}
}