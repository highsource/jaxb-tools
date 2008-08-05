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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.jfrog.maven.annomojo.annotations.MojoComponent;
import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoParameter;
import org.jfrog.maven.annomojo.annotations.MojoPhase;
import org.jfrog.maven.annomojo.annotations.MojoRequiresDependencyResolution;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.ErrorReceiver;
import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

/**
 * JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@MojoGoal("generate")
@MojoPhase("generate-sources")
@MojoRequiresDependencyResolution
public class XJC2Mojo extends AbstractMojo {

	public XJC2Mojo() {

	}

	/**
	 * Returns the version of the artifact as inherited from the dependencies.
	 * 
	 * @param artifact
	 *            the artifact for which to return the version.
	 * @param dependencies
	 *            the list of dependencies in which to search for the version.
	 * @return the version of the artifact, or {@code null} if
	 *         {@code dependencies} is either {@code null} or does not contain
	 *         the artifact's version.
	 */
	protected String getVersionFromDependencies(final Artifact artifact,
			final Set<org.apache.maven.artifact.Artifact> dependencies) {
		if (dependencies == null)
			return null;
		for (Iterator depIterator = dependencies.iterator(); depIterator
				.hasNext(); /* */) {
			org.apache.maven.artifact.Artifact dependency = (org.apache.maven.artifact.Artifact) depIterator
					.next();
			if (artifact.getGroupId().equals(dependency.getGroupId())
					&& artifact.getArtifactId().equals(
							dependency.getArtifactId())) {
				return dependency.getVersion();
			}
		}
		return null;
	}

	private ArtifactResolver artifactResolver;

	@MojoComponent
	public ArtifactResolver getArtifactResolver() {
		return artifactResolver;
	}

	public void setArtifactResolver(ArtifactResolver artifactResolver) {
		this.artifactResolver = artifactResolver;
	}

	private ArtifactFactory artifactFactory;

	/**
	 * Used internally to resolve {@link #plugins} to their jar files.
	 */
	@MojoComponent
	public ArtifactFactory getArtifactFactory() {
		return artifactFactory;
	}

	public void setArtifactFactory(ArtifactFactory artifactFactory) {
		this.artifactFactory = artifactFactory;
	}

	private ArtifactRepository localRepository;

	@MojoParameter(expression = "${localRepository}", required = true)
	public ArtifactRepository getLocalRepository() {
		return localRepository;
	}

	public void setLocalRepository(ArtifactRepository localRepository) {
		this.localRepository = localRepository;
	}

	private MavenProjectBuilder mavenProjectBuilder;

	/**
	 * Artifact factory, needed to download source jars.
	 * 
	 */
	@MojoComponent(role = "org.apache.maven.project.MavenProjectBuilder")
	public MavenProjectBuilder getMavenProjectBuilder() {
		return mavenProjectBuilder;
	}

	public void setMavenProjectBuilder(MavenProjectBuilder mavenProjectBuilder) {
		this.mavenProjectBuilder = mavenProjectBuilder;
	}

	/**
	 * For checking timestamps. Modify freely, nothing to do with XJC options.
	 */
	protected List schemaFiles = new ArrayList();

	/**
	 * For checking timestamps. Modify freely, nothing to do with XJC options.
	 */
	protected List bindingFiles = new ArrayList();

	private String schemaLanguage;

	/**
	 * Type of input schema language. One of: DTD, XMLSCHEMA, RELAXNG,
	 * RELAXNG_COMPACT, WSDL, AUTODETECT. If unspecified, it is assumed
	 * AUTODETECT.
	 */
	@MojoParameter(expression = "${maven.xjc2.schemaLanguage}")
	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public void setSchemaLanguage(String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	private File schemaDirectory;

	/**
	 * The source directory containing *.xsd schema files. Notice that binding
	 * files are searched by default in this deriectory.
	 * 
	 */
	@MojoParameter(defaultValue = "src/main/resources", expression = "${maven.xjc2.schemaDirectory}", required = true)
	public File getSchemaDirectory() {
		return schemaDirectory;
	}

	public void setSchemaDirectory(File schemaDirectory) {
		this.schemaDirectory = schemaDirectory;
	}

	private String[] schemaIncludes = new String[] { "*.xsd" };

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
	 */
	@MojoParameter
	public String[] getSchemaIncludes() {
		return schemaIncludes;
	}

	public void setSchemaIncludes(String[] schemaIncludes) {
		this.schemaIncludes = schemaIncludes;
	}

	private String[] schemaExcludes;

	/**
	 * A list of regular expression file search patterns to specify the schemas
	 * to be excluded from the <code>schemaIncludes</code> list. Searching is
	 * based from the root of schemaDirectory.
	 * 
	 */
	@MojoParameter
	public String[] getSchemaExcludes() {
		return schemaExcludes;
	}

	public void setSchemaExcludes(String[] schemaExcludes) {
		this.schemaExcludes = schemaExcludes;
	}

	private File bindingDirectory;

	public void setBindingDirectory(File bindingDirectory) {
		this.bindingDirectory = bindingDirectory;
	}

	/**
	 * <p>
	 * The source directory containing the *.xjb binding files.
	 * </p>
	 * <p>
	 * If left undefined, then the <code>schemaDirectory</code> is assumed.
	 * </p>
	 */
	@MojoParameter(expression = "${maven.xjc2.bindingDirectory}")
	public File getBindingDirectory() {
		return this.bindingDirectory;
	}

	private String[] bindingIncludes = new String[] { "*.xjb" };

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
	 */
	@MojoParameter
	public String[] getBindingIncludes() {
		return bindingIncludes;
	}

	public void setBindingIncludes(String[] bindingIncludes) {
		this.bindingIncludes = bindingIncludes;
	}

	private String[] bindingExcludes;

	/**
	 * A list of regular expression file search patterns to specify the binding
	 * files to be excluded from the <code>bindingIncludes</code>. Searching
	 * is based from the root of bindingDirectory.
	 */
	@MojoParameter
	public String[] getBindingExcludes() {
		return bindingExcludes;
	}

	public void setBindingExcludes(String[] bindingExcludes) {
		this.bindingExcludes = bindingExcludes;
	}

	private boolean disableDefaultExcludes;

	/**
	 * If 'true', maven's default exludes are NOT added to all the excludes
	 * lists.
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.disableDefaultExcludes}")
	public boolean getDisableDefaultExcludes() {
		return disableDefaultExcludes;
	}

	public void setDisableDefaultExcludes(boolean disableDefaultExcludes) {
		this.disableDefaultExcludes = disableDefaultExcludes;
	}

	private File catalog;

	/**
	 * Specify the catalog file to resolve external entity references (xjc's
	 * -catalog option)
	 * </p>
	 * <p>
	 * Support TR9401, XCatalog, and OASIS XML Catalog format. See the
	 * catalog-resolver sample and this article for details.
	 * </p>
	 */
	@MojoParameter(expression = "${maven.xjc2.catalog}")
	public File getCatalog() {
		return catalog;
	}

	public void setCatalog(File catalog) {
		this.catalog = catalog;
	}

	private String catalogResolver = CatalogResolver.class.getName();

	/**
	 * Provides the class name of the catalog resolver.
	 * 
	 * @return Class name of the catalog resolver.
	 */
	@MojoParameter(expression = "${maven.xjc2.catalogResolver}")
	public String getCatalogResolver() {
		return catalogResolver;
	}

	public void setCatalogResolver(String catalogResolver) {
		this.catalogResolver = catalogResolver;
	}

	private String generatePackage;

	/**
	 * <p>
	 * The generated classes will all be placed under this Java package (xjc's
	 * -p option), unless otherwise specified in the schemas.
	 * </p>
	 * <p>
	 * If left unspecified, the package will be derived from the schemas only.
	 * </p>
	 */
	@MojoParameter(expression = "${maven.xjc2.generatePackage}")
	public String getGeneratePackage() {
		return generatePackage;
	}

	public void setGeneratePackage(String generatePackage) {
		this.generatePackage = generatePackage;
	}

	private File generateDirectory;

	/**
	 * <p>
	 * Generated code will be written under this directory.
	 * </p>
	 * <p>
	 * For instance, if you specify <code>generateDirectory="doe/ray"</code>
	 * and <code>generatePackage="org.here"</code>, then files are generated
	 * to <code>doe/ray/org/here</code>.
	 * </p>
	 */
	@MojoParameter(defaultValue = "${project.build.directory}/generated-sources/xjc", expression = "${maven.xjc2.generateDirectory}", required = true)
	public File getGenerateDirectory() {
		return generateDirectory;
	}

	public void setGenerateDirectory(File generateDirectory) {
		this.generateDirectory = generateDirectory;
	}

	private boolean readOnly;

	/**
	 * If 'true', the generated Java source files are set as read-only (xjc's
	 * -readOnly option).
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.readOnly}")
	public boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	private boolean extension;

	/**
	 * If 'true', the XJC binding compiler will run in the extension mode (xjc's
	 * -extension option). Otherwise, it will run in the strict conformance
	 * mode.
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.extension}")
	public boolean getExtension() {
		return extension;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	private boolean strict;

	/**
	 * If 'true', Perform strict validation of the input schema (xjc's -nv
	 * option).
	 */
	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.strict}")
	public boolean getStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	private boolean writeCode = true;

	/**
	 * If 'false', the plugin will not write the generated code to disk.
	 */
	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.writeCode}")
	public boolean getWriteCode() {
		return writeCode;
	}

	public void setWriteCode(boolean writeCode) {
		this.writeCode = writeCode;
	}

	private boolean verbose;

	/**
	 * <p>
	 * If 'true', the plugin and the XJC compiler are both set to verbose mode
	 * (xjc's -verbose option).
	 * </p>
	 * <p>
	 * It is automatically set to 'true' when maven is run in debug mode (mvn's
	 * -X option).
	 * </p>
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.verbose}")
	public boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private boolean debug;

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
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.debug}")
	public boolean getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	private List<String> args = new LinkedList<String>();

	/**
	 * <p>
	 * A list of extra XJC's command-line arguments (items must include the dash
	 * '-').
	 * </p>
	 * <p>
	 * Arguments set here take precedence over other mojo parameters.
	 * </p>
	 */
	@MojoParameter
	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args.addAll(args);
	}

	private boolean forceRegenerate;

	/**
	 * If 'true', no up-to-date check is performed and the XJC always
	 * re-generates the sources.
	 * 
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.forceRegenerate}")
	public boolean getForceRegenerate() {
		return forceRegenerate;
	}

	public void setForceRegenerate(boolean forceRegenerate) {
		this.forceRegenerate = forceRegenerate;
	}

	private boolean removeOldOutput;

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
	 */
	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.removeOldOutput}")
	public boolean getRemoveOldOutput() {
		return removeOldOutput;
	}

	public void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	private String[] otherDepends;

	/**
	 * A list of of input files or URLs to consider during the up-to-date. By
	 * default it always considers: 1. schema files, 2. binding files, 3.
	 * catalog file, and 4. the pom.xml file of the project executing this
	 * plugin.
	 */
	@MojoParameter
	public String[] getOtherDepends() {
		return otherDepends;
	}

	public void setOtherDepends(String[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	private File episodeFile;

	@MojoParameter(expression = "${maven.xjc2.episodeFile}", defaultValue = "${project.build.directory}/generated-sources/xjc/META-INF/sun-jaxb.episode")
	public File getEpisodeFile() {
		return episodeFile;
	}

	public void setEpisodeFile(File episodeFile) {
		this.episodeFile = episodeFile;
	}

	private boolean episode = true;

	@MojoParameter(expression = "${maven.xjc2.episode}", defaultValue = "true")
	public boolean getEpisode() {
		return episode;
	}

	public void setEpisode(boolean episode) {
		this.episode = episode;
	}

	private List classpathElements;

	/**
	 * Project classpath. Used internally when runing the XJC compiler.
	 */
	@MojoParameter(expression = "${project.compileClasspathElements}", required = true, readonly = true)
	public List getClasspathElements() {
		return classpathElements;
	}

	public void setClasspathElements(List classpathElements) {
		this.classpathElements = classpathElements;
	}

	private MavenProject project;

	@MojoParameter(expression = "${project}", required = true, readonly = true)
	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	private Artifact[] plugins;

	/**
	 * XJC plugins to be made available to XJC. They still need to be activated
	 * by using &lt;args> and enable plugin activation option.
	 */
	@MojoParameter
	public Artifact[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Artifact[] plugins) {
		this.plugins = plugins;
	}

	private Artifact[] episodes;

	@MojoParameter
	public Artifact[] getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Artifact[] episodes) {
		this.episodes = episodes;
	}

	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 */
	public void execute() throws MojoExecutionException {
		try {

			// Install project dependencies into classloader's class path
			// and execute xjc2.
			ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(
					getProjectDepsClassLoader(oldCL));
			try {

				setupLogging();

				// Translate maven plugin options to XJC ones,
				// also perform any sanity checks.
				// SIDE-EFFECT: populates schemaFiles and bindingFiles member
				// vars.
				Options xjcOpts = setupOptions();

				if (getVerbose()) {
					logSettings();
				}

				// Add source path and jaxb resources whether re-generate or
				// not.
				updateMavenPaths();

				// Check whether to re-generate sources.
				if (!this.getForceRegenerate() && isUpdToDate()) {
					getLog()
							.info(
									"Skipped XJC execution.  Generated sources were up-to-date.");
					return;
				}

				// Remove old generated dir.
				if (this.getRemoveOldOutput()) {
					if (this.getGenerateDirectory().exists()) {
						try {
							FileUtils.deleteDirectory(this
									.getGenerateDirectory());
							getLog().info(
									"Removed old generateDirectory '"
											+ this.getGenerateDirectory()
											+ "'.");
						} catch (IOException ex) {
							getLog().warn(
									"Failed to remove old generateDirectory '"
											+ this.getGenerateDirectory()
											+ "' due to: " + ex);
						}

					} else if (getVerbose())
						getLog().info(
								"Skipped removal of old generateDirectory '"
										+ this.getGenerateDirectory()
										+ "' since it didn't exist.");
				}
				// Create the destination path if it does not exist.
				if (getGenerateDirectory() != null
						&& !getGenerateDirectory().exists()) {
					getGenerateDirectory().mkdirs();
				}

				if (getEpisode() && getEpisodeFile() != null) {
					final File parentFile = getEpisodeFile().getParentFile();
					parentFile.mkdirs();
				}

				runXJC(xjcOpts);
				// Inform user about completion.
				getLog()
						.info(
								"Succesfully generated output to: "
										+ xjcOpts.targetDir);

			} finally {
				// Set back the old classloader
				Thread.currentThread().setContextClassLoader(oldCL);
			}

		} catch (RuntimeException ex) {
			getLog()
					.info(
							"Mojo options will be logged due to an unexpected error...");
			logSettings(); // for easy debugging.
			throw ex;
		} catch (MojoExecutionException ex) {
			throw ex;
		}
	}

	/**
	 * Ensure the any default settings are met and throws exceptions when
	 * settings are invalid and also stores the schemas and the bindings files
	 * into member vars for calculating timestamps, later.
	 * 
	 * Exception will cause build to fail.
	 * 
	 * @throws MojoExecutionException
	 */
	protected Options setupOptions() throws MojoExecutionException {
		// DISCOVER XJC ADD-ONS HERE
		// MUST COME AFTER setting "com.sun.tools.xjc.Options.findServices"
		// since this property should be present during classloading.
		Options xjcOpts = new Options();

		xjcOpts.classpaths.addAll(this.getPluginURLs());

		xjcOpts.verbose = this.getVerbose();
		xjcOpts.debugMode = this.getDebug();

		// Setup Schema Language.
		if (!isDefined(getSchemaLanguage(), 1)) {
			setSchemaLanguage("AUTODETECT");
			if (getVerbose())
				getLog()
						.info(
								"The <schemaLanguage> setting was not defined, assuming 'AUTODETECT'.");
		} else if ("AUTODETECT".equalsIgnoreCase(getSchemaLanguage()))
			; // nothing, it is AUTDETECT by default.
		else if ("XMLSCHEMA".equalsIgnoreCase(getSchemaLanguage()))
			xjcOpts.setSchemaLanguage(Language.XMLSCHEMA);
		else if ("DTD".equalsIgnoreCase(getSchemaLanguage()))
			xjcOpts.setSchemaLanguage(Language.DTD);
		else if ("RELAXNG".equalsIgnoreCase(getSchemaLanguage()))
			xjcOpts.setSchemaLanguage(Language.RELAXNG);
		else if ("RELAXNG_COMPACT".equalsIgnoreCase(getSchemaLanguage()))
			xjcOpts.setSchemaLanguage(Language.RELAXNG_COMPACT);
		else if ("WSDL".equalsIgnoreCase(getSchemaLanguage()))
			xjcOpts.setSchemaLanguage(Language.WSDL);
		else {
			logSettings(); // for easy debugging.
			throw new MojoExecutionException("Unknown <schemaLanguage> '"
					+ this.getSchemaLanguage() + "'!");
		}

		List files;
		Iterator it;

		files = gatherFiles(this.getSchemaDirectory(),
				this.getSchemaIncludes(), this.getSchemaExcludes());

		it = files.iterator();
		for (; it.hasNext();)
			xjcOpts.addGrammar(getInputSource((File) it.next()));

		this.schemaFiles.addAll(files);

		// Ensure Schema files exist.
		if (this.schemaFiles.size() == 0) {
			logSettings(); // for easy debugging.
			throw new MojoExecutionException(
					"No schemas found inside the <schemaDirectory> '"
							+ this.getSchemaDirectory() + "'!");
		}

		// Setup binding files.
		if (!isDefined(getBindingDirectory(), 1)) {
			setBindingDirectory(getSchemaDirectory());
			if (getVerbose())
				getLog()
						.info(
								"The <bindingDirectory> setting was not defined, assuming the same as <schemaDirectory>: "
										+ getSchemaDirectory() + "");
		}

		files = gatherFiles(this.getBindingDirectory(), this
				.getBindingIncludes(), this.getBindingExcludes());

		it = files.iterator();
		for (; it.hasNext();)
			xjcOpts.addBindFile(getInputSource((File) it.next()));

		this.bindingFiles.addAll(files);

		// Setup Catalog files (XML Entity Resolver).
		if (isDefined(this.getCatalog(), 1)) {
			try {
				xjcOpts.addCatalog(this.getCatalog());
			} catch (IOException ex) {
				logSettings(); // for easy debugging.
				throw new MojoExecutionException(
						"Error while setting the <catalog> to '"
								+ this.getCatalog() + "'!", ex);
			}
		}

		configureCatalogResolver(xjcOpts);

		// Setup Other Options

		xjcOpts.defaultPackage = this.getGeneratePackage();
		xjcOpts.targetDir = this.getGenerateDirectory();

		xjcOpts.strictCheck = this.getStrict();
		xjcOpts.readOnly = this.getReadOnly();

		if (this.getExtension())
			xjcOpts.compatibilityMode = Options.EXTENSION;

		setupCmdLineArgs(xjcOpts);

		return xjcOpts;
	}

	protected void configureCatalogResolver(Options options)
			throws MojoExecutionException {
		if (catalogResolver != null) {

			try {
				Class<?> draftCatalogResolverClass = Thread.currentThread()
						.getContextClassLoader().loadClass(catalogResolver);
				if (!CatalogResolver.class
						.isAssignableFrom(draftCatalogResolverClass)) {
					throw new MojoExecutionException(
							"Specified catalog resolver class ["
									+ catalogResolver
									+ "] could not be casted to ["
									+ CatalogResolver.class + "].");
				} else {
					final Class<? extends CatalogResolver> catalogResolverClass = (Class<? extends CatalogResolver>) draftCatalogResolverClass;
					final CatalogResolver catalogResolverInstance = catalogResolverClass
							.newInstance();
					options.entityResolver = catalogResolverInstance;
				}
			} catch (ClassNotFoundException cnfex) {
				throw new MojoExecutionException(
						"Could not find specified catalog resolver class ["
								+ catalogResolver + "].", cnfex);
			} catch (InstantiationException iex) {
				throw new MojoExecutionException(
						"Could not instantiate catalog resolver class ["
								+ catalogResolver + "].", iex);
			} catch (IllegalAccessException iaex) {
				throw new MojoExecutionException(
						"Could not instantiate catalog resolver class ["
								+ catalogResolver + "].", iaex);
			}

		}
	}

	protected void setupCmdLineArgs(Options xjcOpts)
			throws MojoExecutionException {
		if (getEpisode() && getEpisodeFile() != null) {
			getArgs().add("-episode");
			getArgs().add(getEpisodeFile().getAbsolutePath());
		}

		if (getEpisodes() != null) {
			final Collection<File> episodeFiles = getEpisodeFiles();

			for (final File episodeFile : episodeFiles) {
				getArgs().add(episodeFile.getAbsolutePath());
			}
		}

		if (!getArgs().isEmpty()) {
			try {
				xjcOpts.parseArguments((String[]) getArgs().toArray(
						new String[getArgs().size()]));
			} catch (BadCommandLineException ex) {
				throw new MojoExecutionException(
						"Error while setting CmdLine <args> options '"
								+ this.getArgs() + "'!", ex);
			}
		}
	}

	/**
	 * Sets up the verbose and debug mode depending on mvn logging level, and
	 * sets up hyperjaxb logging.
	 */
	protected void setupLogging() {
		Log log = getLog();

		// Maven's logging level should propagate to
		// plugin's verbose/debug mode.
		if (log.isDebugEnabled())
			this.setDebug(true);

		if (this.getDebug()) {
			// If not verbose, debug messages would be lost.
			this.setVerbose(true);

			// Also print XJC add-on plugins instanciation messages.
			// NOTE that it must happend BEFORE 'Options' constructor.
			System
					.setProperty("com.sun.tools.xjc.Options.findServices",
							"true");
		}
	}

	/**
	 * Log the configuration settings. Shown when exception thrown or when
	 * verbose is true.
	 */
	protected void logSettings() {
		StringBuffer sb = new StringBuffer();

		sb.append("Plugin's mojo parameters: ");
		logUserSettings(sb);

		sb.append("\nCalculated options: ");
		logCalcSettings(sb);

		getLog().info(sb);
	}

	/**
	 * Logs options defined directly as mojo parameters.
	 */
	protected void logUserSettings(StringBuffer sb) {
		sb.append("\n\tschemaLanguage: " + getSchemaLanguage());
		sb.append("\n\tschemaDirectory: " + getSchemaDirectory());
		sb.append("\n\tschemaIncludes: "
				+ recursiveToString(getSchemaIncludes()));
		sb.append("\n\tschemaExcludes: "
				+ recursiveToString(getSchemaExcludes()));
		sb.append("\n\tbindingDirectory: " + getBindingDirectory());
		sb.append("\n\tbindingIncludes: "
				+ recursiveToString(getBindingIncludes()));
		sb.append("\n\tbindingExcludes: "
				+ recursiveToString(getBindingExcludes()));
		sb.append("\n\tdisableDefaultExcludes: " + getDisableDefaultExcludes());
		sb.append("\n\tcatalog: " + getCatalog());
		sb.append("\n\tdefaultPackage: " + getGeneratePackage());
		sb.append("\n\tdestinationDirectory: " + getGenerateDirectory());
		sb.append("\n\tforceRegenerate: " + getForceRegenerate());
		sb.append("\n\totherDepends: " + recursiveToString(getOtherDepends()));
		sb.append("\n\tremoveOldOutput: " + getRemoveOldOutput());
		sb.append("\n\twriteCode: " + getWriteCode());
		sb.append("\n\treadOnly: " + getReadOnly());
		sb.append("\n\textension: " + getExtension());
		sb.append("\n\tstrict: " + getStrict());
		sb.append("\n\tverbose: " + getVerbose());
		sb.append("\n\tdebug: " + getDebug());
		sb.append("\n\txjcArgs: " + recursiveToString(getArgs()));
	}

	/**
	 * Logs options calculated by mojo parameters.
	 */
	protected void logCalcSettings(StringBuffer sb) {
		sb.append("\n\tSchema File(s): " + recursiveToString(schemaFiles));
		sb.append("\n\tBinding File(s): " + recursiveToString(bindingFiles));
		sb
				.append("\n\tClassPath: "
						+ recursiveToString(getClasspathElements()));
	}

	/**
	 * @return true to indicate results are up-to-date, that is, when the latest
	 *         from input files is earlier than the younger from the output
	 *         files (meaning no re-execution required).
	 */
	protected boolean isUpdToDate() throws MojoExecutionException {
		List dependsFiles = new ArrayList();
		List producesFiles = new ArrayList();

		gatherDependsFiles(dependsFiles);
		if (getVerbose() && !getDebug()) // If debug, they are printed along
			// with
			// modifTime.
			getLog().info(
					"Checking up-to-date depends: "
							+ recursiveToString(dependsFiles));

		gatherProducesFiles(producesFiles);
		if (getVerbose() && !getDebug()) // If debug, they are printed along
			// with
			// modifTime.
			getLog().info(
					"Checking up-to-date produces: "
							+ recursiveToString(producesFiles));
		// ////////////////
		// Perform the timestamp comparison.
		// ////////////////

		// The older timeStamp of all input files;
		long inputTimeStamp = findLastModified(dependsFiles, true);

		// The younger of all destination files.
		long destTimeStamp = findLastModified(producesFiles, false);

		if (getVerbose())
			getLog().info(
					"Depends timeStamp: " + inputTimeStamp
							+ ", produces timestamp: " + destTimeStamp);

		return inputTimeStamp < destTimeStamp;
	}

	protected void gatherDependsFiles(List inputFiles) {
		inputFiles.addAll(this.schemaFiles);
		inputFiles.addAll(this.bindingFiles);
		if (this.getCatalog() != null)
			inputFiles.add(this.getCatalog());

		// Pom dependency - typically when configuration settings change.
		if (getProject() != null) { // null when run for testing.
			inputFiles.add(getProject().getFile());
		}

		// Gather user-defined files and URLs.
		if (this.getOtherDepends() != null)
			inputFiles.addAll(Arrays.asList(this.getOtherDepends()));
	}

	protected void gatherProducesFiles(List destFiles)
			throws MojoExecutionException {
		if (this.getGenerateDirectory().exists()) {
			if (!this.getGenerateDirectory().isDirectory())
				getLog()
						.warn(
								"The <generateDirectory>='"
										+ getGenerateDirectory()
										+ "' is not a directory!  Probably XJC will fail...");

			else
				destFiles.addAll(gatherFiles(this.getGenerateDirectory(),
						new String[] { "**/*.java", "**/bgm.ser",
								"**/jaxb.properties" }, null));
		}
	}

	/**
	 * @param parent
	 *            the returned classLoader will be a descendant of this one.
	 * @return a context class loader with a classPath containing the project
	 *         dependencies.
	 */
	private ClassLoader getProjectDepsClassLoader(ClassLoader parent) {
		List urls = new ArrayList();
		if (this.getClasspathElements() != null) // For instance, when run
			// for testing.
			for (Iterator it = this.getClasspathElements().iterator(); it
					.hasNext();) {
				String pathElem = (String) it.next();
				try {
					urls.add(new File(pathElem).toURI().toURL());
				} catch (MalformedURLException e) {
					getLog().warn(
							"Internal classpath element '" + pathElem
									+ "' is was skiped due to: "
									+ getAllExMsgs(e, true));
				}
			}

		ClassLoader cl = new XJC2MojoClassLoader((URL[]) urls
				.toArray(new URL[urls.size()]), parent);
		return cl;
	}

	/**
	 * 
	 * @param xjcOpts
	 * @throws MojoExecutionException
	 */
	protected void runXJC(Options xjcOpts) throws MojoExecutionException {
		JaxbErrorReceiver4Mvn errorReceiver = new JaxbErrorReceiver4Mvn();

		Model model;
		if (getVerbose())
			getLog().info("Parsing input schema(s)...");

		errorReceiver.stage = "parsing";
		model = ModelLoader.load(xjcOpts, new JCodeModel(), errorReceiver);

		if (model == null)
			throw new MojoExecutionException(
					"Unable to parse input schema(s).  Error messages should have been provided.");

		try {

			if (getVerbose())
				getLog().info("Compiling input schema(s)...");

			errorReceiver.stage = "compiling";

			{
				final Outline outline = model.generateCode(xjcOpts,
						errorReceiver);
				if (outline == null) {
					throw new MojoExecutionException(
							"Failed to compile input schema(s)!  Error messages should have been provided.");
				}
			}
			// if(Driver.generateCode( model, xjcOpts, errorReceiver )==null)
			// throw new MojoExecutionException("Failed to compile input
			// schema(s)! Error messages should have been provided.");

			if (getWriteCode()) {
				if (getVerbose())
					getLog().info("Writing output to: " + xjcOpts.targetDir);

				model.codeModel.build(new JaxbCodeWriter4Mvn(xjcOpts
						.createCodeWriter()));
			} else {
				if (getVerbose())
					getLog().info("Code will not be written.");
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Unable to write files: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Updates compilePath ans resources.
	 * 
	 * @throws MojoExecutionException
	 */
	protected void updateMavenPaths() {
		// Mark XJC_generated java files for compilation.
		if (getProject() != null) // null when run for testing.
			getProject().addCompileSourceRoot(getGenerateDirectory().getPath());

		// Mark XJC_gen prop files as resources for inclusion to final jar.
		Resource jaxbRes = new Resource();
		jaxbRes.setDirectory(getGenerateDirectory().getPath());
		jaxbRes.addInclude("**/jaxb.properties");
		jaxbRes.addInclude("**/bgm.ser");

		if (getProject() != null) // null when run for testing.
			getProject().addResource(jaxbRes);

		if (getEpisode() && getEpisodeFile() != null) {
			final String episodeFilePath = getEpisodeFile().getAbsolutePath();
			final String generatedDirectoryPath = getGenerateDirectory()
					.getAbsolutePath();

			if (episodeFilePath.startsWith(generatedDirectoryPath
					+ File.separator)) {
				final String path = episodeFilePath
						.substring(generatedDirectoryPath.length() + 1);

				final Resource resource = new Resource();
				resource.setDirectory(generatedDirectoryPath);
				resource.addInclude(path);
				getProject().addResource(resource);
			}
		}
	}

	/**
	 * 
	 * @param files
	 *            the fileNames or URLs to scan their lastModified timestamp.
	 * @param oldest
	 *            if true, returns the latest modificationDate of all files,
	 *            otherwise returns the earliest.
	 * @return the older or younger last modification timestamp of all files.
	 */
	protected long findLastModified(List/* <Object> */files, boolean oldest) {
		long timeStamp = (oldest ? Long.MIN_VALUE : Long.MAX_VALUE);
		for (Iterator it = files.iterator(); it.hasNext();) {
			Object no = it.next();

			if (no != null) {
				long fileModifTime;
				if (no instanceof String) {// either URL or file
					String sdep = (String) no;

					try {
						URL url = new URL(sdep);

						URLConnection uCon = url.openConnection();
						uCon.setUseCaches(false);
						// //uCon.setConnectTimeout(5000);// JDK1.5 only!!!

						fileModifTime = uCon.getLastModified();

					} catch (MalformedURLException e) {
						fileModifTime = new File(sdep).lastModified();

					} catch (IOException ex) {
						fileModifTime = (oldest ? Long.MIN_VALUE
								: Long.MAX_VALUE);
						getLog()
								.warn(
										"Skipping URL '"
												+ no
												+ "' from up-to-date check due to error while opening connection: "
												+ getAllExMsgs(ex, true));
					}

				} else
					// asume instanceof File
					fileModifTime = ((File) no).lastModified();

				if (getDebug())
					getLog().info(
							(oldest ? "Depends " : "Produces ") + no + ": "
									+ new Date(fileModifTime));

				if ((fileModifTime > timeStamp) ^ !oldest)
					timeStamp = fileModifTime;
			} // end if file null.
		}// end filesloop

		if (timeStamp == Long.MIN_VALUE) // no older file found
			return Long.MAX_VALUE; // assume re-execution required.
		else if (timeStamp == Long.MAX_VALUE) // no younger file found
			return Long.MIN_VALUE; // assume re-execution required.

		return timeStamp;
	}

	/**
	 * Converts a File object to an InputSource.
	 */
	protected static InputSource getInputSource(File f) {
		try {
			return new InputSource(f.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			return new InputSource(f.getPath());
		}
	}

	/**
	 * 
	 * @param baseDir
	 * @param includesPattern
	 * @param excludesPattern
	 * @return all found absolute files (dirs and files).
	 */
	protected List/* <File> */gatherFiles(File baseDir,
			String[] includesPattern, String[] excludesPattern)
			throws MojoExecutionException {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(baseDir.getAbsoluteFile());
		scanner.setIncludes(includesPattern);
		scanner.setExcludes(getExcludes(excludesPattern));

		scanner.scan();

		List files = new ArrayList();
		String[] incFiles = scanner.getIncludedFiles();
		for (int i = 0; i < incFiles.length; i++) {
			String name = incFiles[i];
			try {
				File file = new File(baseDir, name);
				files.add(file.getCanonicalFile());
			} catch (IOException ex) {
				throw new MojoExecutionException(
						"Unable to canonize the file [" + name + "]");
			}
		}

		return files;
	}

	protected String[] getExcludes(String[] origExcludes) {
		if (origExcludes == null)
			return null;
		final List ex = new ArrayList<String>(Arrays.asList(origExcludes));
		List newExc = getExcludes(ex);
		return (String[]) newExc.toArray(new String[newExc.size()]);
	}

	/**
	 * Modifies input list by adding plexus tools default excludes.
	 * 
	 * @param origExcludes
	 *            a list that must support the 'add' operation.
	 * @return the augmented list or the input unchanged.
	 */
	protected List/* <String> */getExcludes(List/* <String> */origExcludes) {
		if (origExcludes == null || this.getDisableDefaultExcludes())
			return origExcludes;

		origExcludes.addAll(Arrays.asList(FileUtils.getDefaultExcludes()));

		return origExcludes;
	}

	/**
	 * A generic setting validator. Check for null and zero length of strings,
	 * arrays and collections.
	 * 
	 * @param setting
	 *            the settings to validate
	 * @param minimumLength
	 *            minimum length required.
	 * @return true if setting is not null and has length or more items.
	 */
	protected static boolean isDefined(Object setting, int minimumLength) {
		boolean defined = setting != null;

		if (setting instanceof Object[]) {
			defined = defined && ((Object[]) setting).length >= minimumLength;
		} else if (setting instanceof Collection) {
			defined = defined && ((Collection) setting).size() >= minimumLength;
		} else {
			defined = defined
					&& setting.toString().trim().length() >= minimumLength;
		}

		return defined;
	}

	/**
	 * A generic approach to turning the values inside arrays and collections
	 * into toString values.
	 * 
	 * @param setting
	 * @return complete toString values for most contained objects.
	 */
	protected static String recursiveToString(Object setting) {
		if (setting == null)
			return "null";

		if (setting instanceof Collection) {
			Collection collection = (Collection) setting;
			setting = collection.toArray();
		}

		if (setting instanceof Object[]) {
			Object[] settingArray = (Object[]) setting;
			if (settingArray.length == 0)
				return "[]";

			StringBuffer result = new StringBuffer();

			result.append('[');
			result.append(recursiveToString(settingArray[0]));
			for (int index = 1; index < settingArray.length; index++) {
				result.append(", ");
				result.append(recursiveToString(settingArray[index]));
			}
			result.append(']');

			return result.toString();
		}

		return setting.toString();
	}

	public static String getAllExMsgs(Throwable ex, boolean includeExName) {
		StringBuffer sb = new StringBuffer((includeExName ? ex.toString() : ex
				.getLocalizedMessage()));

		Throwable cause = ex.getCause();
		Exception embeded = ex instanceof SAXParseException ? ((SAXParseException) ex)
				.getException()
				: null;
		// Fisrt check that embeded and cause are the same,
		// then process each one.
		if ((cause == embeded && cause != null) || cause != null)
			getAllCauseExMsgs(cause, includeExName, sb);
		else if (embeded != null)
			getAllCauseExMsgs(embeded, includeExName, sb);

		return sb.toString();
	}

	private static void getAllCauseExMsgs(Throwable ex, boolean includeExName,
			StringBuffer sb) {
		do {
			sb
					.append("\nCaused by: "
							+ (includeExName ? ex.toString() : ex
									.getLocalizedMessage()));
		} while ((ex = ex.getCause()) != null);
	}

	public static String getAllExStackTraces(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		ex.printStackTrace(pw);

		Throwable cause = ex.getCause();
		Exception embeded = ex instanceof SAXParseException ? ((SAXParseException) ex)
				.getException()
				: null;

		if (embeded != null && cause != embeded) {
			pw.append("Embedded ex:");
			embeded.printStackTrace(pw);
		}

		return sw.toString();
	}

	protected class JaxbErrorReceiver4Mvn extends ErrorReceiver {

		public String stage = "processing";

		public void warning(SAXParseException e) {
			getLog().warn(makeMessage(e, true));
		}

		public void error(SAXParseException e) {
			getLog().error(makeMessage(e, true));
		}

		public void fatalError(SAXParseException e) {
			getLog().error(makeMessage(e, true));
		}

		public void info(SAXParseException e) {
			if (getVerbose())
				getLog().info(makeMessage(e, false));
		}

		private String makeMessage(SAXParseException ex, boolean printExName) {
			int row = ex.getLineNumber();
			int col = ex.getColumnNumber();
			String sys = ex.getSystemId();
			String pub = ex.getPublicId();

			String exString;
			if (getDebug()) {
				exString = getAllExStackTraces(ex);
			} else
				exString = getAllExMsgs(ex, printExName);

			return "XJC while "
					+ stage
					+ " schema(s)"
					+ (sys != null ? " " + sys : "")
					+ (pub != null ? " " + pub : "")
					+ (row > 0 ? "[" + row + (col > 0 ? "," + col : "") + "]"
							: "") + ": " + exString;
		}
	}

	private class JaxbCodeWriter4Mvn extends CodeWriter {
		private final CodeWriter output;

		public JaxbCodeWriter4Mvn(CodeWriter output) {
			this.output = output;
		}

		public Writer openSource(JPackage pkg, String fileName)
				throws IOException {
			if (getVerbose()) {
				if (pkg.isUnnamed())
					getLog().info("XJC writing: " + fileName);
				else
					getLog().info(
							"XJC writing: "
									+ pkg.name().replace('.',
											File.separatorChar)
									+ File.separatorChar + fileName);
			}

			return output.openSource(pkg, fileName);
		}

		public OutputStream openBinary(JPackage pkg, String fileName)
				throws IOException {
			if (getVerbose()) {
				if (pkg.isUnnamed())
					getLog().info("XJC writing: " + fileName);
				else
					getLog().info(
							"XJC writing: "
									+ pkg.name().replace('.',
											File.separatorChar)
									+ File.separatorChar + fileName);
			}

			return output.openBinary(pkg, fileName);
		}

		public void close() throws IOException {
			output.close();
		}

	}

	public Collection<URL> getPluginURLs() throws MojoExecutionException {
		final Collection<URL> pluginURLs = new HashSet<URL>();
		for (File artifactFile : getArtifactFiles(getPlugins(), true)) {
			try {
				pluginURLs.add(artifactFile.toURI().toURL());
			} catch (MalformedURLException ex) {
				throw new MojoExecutionException(
						"Could not retrieve URL for a file.", ex);
			}
		}
		return pluginURLs;
	}

	public Collection<File> getEpisodeFiles() throws MojoExecutionException {
		return getArtifactFiles(getEpisodes(), false);
	}

	protected Collection<File> getArtifactFiles(final Artifact[] artifacts,
			boolean resolveDependencies) throws MojoExecutionException {
		final Set<File> files = new HashSet<File>();
		final Set<org.apache.maven.artifact.Artifact> projectDependencies = getProject()
				.getDependencyArtifacts();
		if (artifacts != null) {
			for (int i = 0; i < artifacts.length; i++) {
				// org.apache.maven.artifact.Artifact artifact = artifacts[i]
				// .toArtifact(getArtifactFactory());
				org.apache.maven.artifact.Artifact artifact = null;
				if (artifacts[i].getVersion() != null
						|| projectDependencies == null) {
					// use version information given by the user
					artifact = artifacts[i].toArtifact(getArtifactFactory());
				} else {
					// try to resolve missing version information from the
					// dependencies
					Artifact withVersion = new Artifact();
					withVersion.setGroupId(artifacts[i].getGroupId());
					withVersion.setArtifactId(artifacts[i].getArtifactId());
					withVersion.setVersion(getVersionFromDependencies(
							artifacts[i], projectDependencies));
					getLog()
							.info(
									"No version specified for plugin/episode-artifact. "
											+ "Resolving version from dependencies yields "
											+ withVersion.toString());
					artifact = withVersion.toArtifact(getArtifactFactory());
				}
				try {
					getArtifactResolver().resolve(artifact,
							getProject().getRemoteArtifactRepositories(),
							localRepository);
					files.add(artifact.getFile());
					if (resolveDependencies && !artifact.isOptional()) {
						final Set<org.apache.maven.artifact.Artifact> artifactDependencies = resolveArtifactDependencies(artifact);
						for (Iterator iterator = artifactDependencies
								.iterator(); iterator.hasNext();) {
							org.apache.maven.artifact.Artifact artifactDependency = (org.apache.maven.artifact.Artifact) iterator
									.next();
							// getArtifactResolver().resolve(
							// artifactDependency,
							// getProject()
							// .getRemoteArtifactRepositories(),
							// localRepository);

							files.add(artifactDependency.getFile());
						}
					}

				} catch (ArtifactResolutionException e) {
					throw new MojoExecutionException(
							"Error attempting to download the plugin: "
									+ artifacts[i], e);
				} catch (ArtifactNotFoundException e) {
					throw new MojoExecutionException("Plugin doesn't exist: "
							+ artifacts[i], e);
				} catch (ProjectBuildingException e) {
					throw new MojoExecutionException(
							"Error processing the plugin dependency POM.", e);
				} catch (InvalidDependencyVersionException e) {
					throw new MojoExecutionException(
							"Invalid plugin dependency version.", e);
				}
			}
		}
		return files;
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
	protected Set<org.apache.maven.artifact.Artifact> resolveArtifactDependencies(
			org.apache.maven.artifact.Artifact artifact)
			throws ArtifactResolutionException, ArtifactNotFoundException,
			ProjectBuildingException, InvalidDependencyVersionException {
		final org.apache.maven.artifact.Artifact pomArtifact = getArtifactFactory()
				.createArtifact(artifact.getGroupId(),
						artifact.getArtifactId(), artifact.getVersion(), "",
						"pom");

		final MavenProject pomProject = getMavenProjectBuilder()
				.buildFromRepository(pomArtifact,
						getProject().getRemoteArtifactRepositories(),
						getLocalRepository());

		final Set<org.apache.maven.artifact.Artifact> artifacts = new HashSet<org.apache.maven.artifact.Artifact>();

		final Set<org.apache.maven.artifact.Artifact> dependencyArtifacts = resolveDependencyArtifacts(pomProject);

		artifacts.addAll(dependencyArtifacts);
		if (!dependencyArtifacts.isEmpty()) {
			for (org.apache.maven.artifact.Artifact a : dependencyArtifacts) {
				if (!a.isOptional()) {
					artifacts.addAll(resolveArtifactDependencies(a));
				}
			}
		}
		return artifacts;
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
	@SuppressWarnings("unchecked")
	protected Set<org.apache.maven.artifact.Artifact> resolveDependencyArtifacts(
			MavenProject theProject) throws ArtifactResolutionException,
			ArtifactNotFoundException, InvalidDependencyVersionException {

		final Set<org.apache.maven.artifact.Artifact> as = new HashSet<org.apache.maven.artifact.Artifact>();

		final Set<org.apache.maven.artifact.Artifact> artifacts = theProject
				.createArtifacts(
						getArtifactFactory(),
						org.apache.maven.artifact.Artifact.SCOPE_RUNTIME,
						new ScopeArtifactFilter(
								org.apache.maven.artifact.Artifact.SCOPE_RUNTIME));

		for (org.apache.maven.artifact.Artifact artifact : artifacts) {
			if (!artifact.isOptional()) {
				try {
					getArtifactResolver().resolve(artifact,
							getProject().getRemoteArtifactRepositories(),
							getLocalRepository());
					as.add(artifact);
				} catch (ArtifactNotFoundException arex) {
					getLog().warn("Error resolving plugin dependency.", arex);
				} catch (ArtifactResolutionException arex) {
					getLog().warn("Error resolving plugin dependency.", arex);
				}
			}
		}
		return as;
	}

}
