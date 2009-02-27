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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
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
import org.jvnet.jaxb2.maven2.util.CollectionUtils;
import org.jvnet.jaxb2.maven2.util.IOUtils;
import org.jvnet.jaxb2.maven2.util.StringUtils;
import org.xml.sax.InputSource;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

/**
 * Maven JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@MojoGoal("generate")
@MojoPhase("generate-sources")
@MojoRequiresDependencyResolution
public class RawXJC2Mojo extends AbstractXJC2Mojo {

	private MavenProject project;

	@MojoParameter(expression = "${project}", required = true, readonly = true)
	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
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

	/*****************************************************************************/

	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 */
	public void execute() throws MojoExecutionException {
		// Install project dependencies into classloader's class path
		// and execute xjc2.
		final ClassLoader currentClassLoader = Thread.currentThread()
				.getContextClassLoader();
		final ClassLoader newClassLoader = getProjectDepsClassLoader(currentClassLoader);
		Thread.currentThread().setContextClassLoader(newClassLoader);
		try {
			doExecute();

		} finally {
			// Set back the old classloader
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	protected void doExecute() throws MojoExecutionException {
		setupLogging();
		if (getVerbose())
			getLog().info("Started execution.");
		setupMavenPaths();
		setupDirectories();
		setupFiles();
		if (getVerbose()) {
			logConfiguration();
		}

		final Options options = setupOptions();

		if (!this.getForceRegenerate() && isUpToDate()) {
			getLog()
					.info(
							"Skipped XJC execution.  Generated sources were up-to-date.");
			return;
		}

		final Model model = loadModel(options);
		final Outline outline = generateCode(model);
		writeCode(outline);
		if (getVerbose())
			getLog().info("Finished execution.");
	}

	/*****************************************************************************/

	/**
	 * @return true to indicate results are up-to-date, that is, when the latest
	 *         from input files is earlier than the younger from the output
	 *         files (meaning no re-execution required).
	 */
	protected boolean isUpToDate() throws MojoExecutionException {
		final List<File> dependsFiles = getDependsFiles();
		final List<File> producesFiles = getProducesFiles();

		if (getVerbose())
			getLog()
					.info("Checking up-to-date depends [" + dependsFiles + "].");

		if (getVerbose())
			getLog().info("Checking up-to-date produces [" + producesFiles + "].");

		final CollectionUtils.Function<File, Long> lastModified = new CollectionUtils.Function<File, Long>() {
			public Long eval(File file) {
				return getLastModified(file);
			}
		};
		final Long dependsTimestamp = CollectionUtils.bestValue(dependsFiles,
				lastModified, CollectionUtils.<Long> gt());
		final Long producesTimestamp = CollectionUtils.bestValue(producesFiles,
				lastModified, CollectionUtils.<Long> lt());

		if (getVerbose())
			getLog().info(
					"Depends timestamp [" + dependsTimestamp
							+ "], produces timestamp [" + producesTimestamp
							+ "].");

		return CollectionUtils.<Long> lt().compare(dependsTimestamp,
				producesTimestamp) > 0;
	}

	protected long getLastModified(File file) {
		if (file == null || !file.exists()) {
			return 0;
		} else {
			return file.lastModified();
		}
	}

	protected List<File> getDependsFiles() {
		final List<File> dependsFiles = new ArrayList<File>();

		dependsFiles.addAll(getSchemaFiles());
		dependsFiles.addAll(getBindingFiles());
		final File catalog = getCatalog();
		if (catalog != null) {
			dependsFiles.add(catalog);
		}
		dependsFiles.add(getProject().getFile());
		if (getOtherDepends() != null) {
			dependsFiles.addAll(Arrays.asList(getOtherDepends()));
		}
		return dependsFiles;

	}

	protected List<File> getProducesFiles() throws MojoExecutionException {
		return getFiles(getGenerateDirectory(), new String[] { "**/*.*", "**/*.java",
				"**/bgm.ser", "**/jaxb.properties" }, new String[0], false);

	}
	
	
	/*****************************************************************************/

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
									+ "' is was skiped due to.", e);
				}
			}

		ClassLoader cl = new XJC2MojoClassLoader((URL[]) urls
				.toArray(new URL[urls.size()]), parent);
		return cl;
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

	/**
	 * Returns the version of the artifact as inherited from the dependencies.
	 * 
	 * @param artifact
	 *            the artifact for which to return the version.
	 * @param dependencies
	 *            the list of dependencies in which to search for the version.
	 * @return the version of the artifact, or {@code null} if {@code
	 *         dependencies} is either {@code null} or does not contain the
	 *         artifact's version.
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

	/****************************************************************************/

	protected Collection<File> getEpisodeFiles() throws MojoExecutionException {
		return getArtifactFiles(getEpisodes(), false);
	}

	/**
	 * Initializes logging. If Maven is run in debug mode (that is, debug level
	 * is enabled in the log), turn on the verbose mode in Mojo. Further on, if
	 * vebose mode is on, set the
	 * <code>com.sun.tools.xjc.Options.findServices</code> system property on to
	 * enable debuggin of XJC plugins.
	 * 
	 */
	protected void setupLogging() {

		setVerbose(getVerbose() || getLog().isDebugEnabled());

		if (getVerbose()) {
			System
					.setProperty("com.sun.tools.xjc.Options.findServices",
							"true");
		}

	}

	/**
	 * Log the configuration settings. Shown when exception thrown or when
	 * verbose is true.
	 */
	protected void logConfiguration() {
		super.logConfiguration();
		getLog().info("schemaFiles (calculated):" + getSchemaFiles());
		getLog().info("bindingFiles (calculated):" + getBindingFiles());
	}

	/**
	 * Augments Maven paths with generated resources.
	 */
	protected void setupMavenPaths() {
		getProject().addCompileSourceRoot(getGenerateDirectory().getPath());

		// Mark XJC_gen prop files as resources for inclusion to final jar.
		final Resource generatedResource = new Resource();
		generatedResource.setDirectory(getGenerateDirectory().getPath());
		generatedResource.addInclude("**/jaxb.properties");
		generatedResource.addInclude("**/bgm.ser");

		getProject().addResource(generatedResource);

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
	 * Creates and initializes an instance of XJC options.
	 * 
	 */
	protected Options setupOptions() throws MojoExecutionException {
		final Options options = new Options();

		options.classpaths.addAll(getPluginURLs());
		options.verbose = this.getVerbose();
		options.debugMode = this.getDebug();

		final Language schemaLanguage = getLanguage();
		if (schemaLanguage != null) {
			options.setSchemaLanguage(schemaLanguage);
		}

		for (InputSource grammar : getGrammars()) {
			options.addGrammar(grammar);
		}

		for (InputSource bindFile : getBindFiles()) {
			options.addBindFile(bindFile);
		}

		final CatalogResolver catalogResolver = getCatalogResolverInstance();
		if (catalogResolver != null) {
			options.entityResolver = catalogResolver;
		}

		// Setup Catalog files (XML Entity Resolver).
		final File catalog = this.getCatalog();
		if (catalog != null) {
			try {
				options.addCatalog(catalog);
			} catch (IOException ioex) {
				throw new MojoExecutionException(
						"Error while setting the catalog to ["
								+ catalog.getAbsolutePath() + "].", ioex);
			}
		}

		// Setup Other Options

		options.defaultPackage = getGeneratePackage();
		options.targetDir = getGenerateDirectory();

		options.strictCheck = this.getStrict();
		options.readOnly = this.getReadOnly();

		if (getExtension()) {
			options.compatibilityMode = Options.EXTENSION;
		}

		final String[] arguments = getArguments();
		try {
			options.parseArguments(arguments);
		}

		catch (BadCommandLineException bclex) {
			throw new MojoExecutionException("Error parsing the command line ["
					+ arguments + "]", bclex);
		}

		return options;
	}

	protected Model loadModel(Options options) throws MojoExecutionException {
		if (getVerbose()) {
			getLog().info("Parsing input schema(s)...");
		}
		final Model model = ModelLoader.load(options, new JCodeModel(),
				new LoggingErrorReceiver("Error while parsing schema(s).",
						getLog(), getVerbose()));

		if (model == null)
			throw new MojoExecutionException(
					"Unable to parse input schema(s). Error messages should have been provided.");
		return model;
	}

	protected Outline generateCode(final Model model)
			throws MojoExecutionException {
		if (getVerbose()) {
			getLog().info("Compiling input schema(s)...");
		}

		final Outline outline = model.generateCode(model.options,
				new LoggingErrorReceiver("Error while generating code.",
						getLog(), getVerbose()));
		if (outline == null) {
			throw new MojoExecutionException(
					"Failed to compile input schema(s)!  Error messages should have been provided.");
		} else {
			return outline;
		}
	}

	protected void writeCode(Outline outline) throws MojoExecutionException {

		if (getWriteCode()) {
			if (getVerbose())
				getLog().info(
						"Writing output to ["
								+ outline.getModel().options.targetDir
										.getAbsolutePath() + "].");

			try {
				final CodeWriter codeWriter = new LoggingCodeWriter(outline
						.getModel().options.createCodeWriter(), getLog(),
						getVerbose());
				outline.getModel().codeModel.build(codeWriter);
			} catch (IOException e) {
				throw new MojoExecutionException("Unable to write files: "
						+ e.getMessage(), e);
			}
		} else {
			getLog().info("Code will not be written.");
		}
	}

	/**
	 * Returns the internal schema language as enum, or <code>null</code> for
	 * autodetect.
	 * 
	 * @return Internal schema language.
	 */
	protected Language getLanguage() throws MojoExecutionException {
		// Setup Schema Language.
		final String schemaLanguage = getSchemaLanguage();
		if (StringUtils.isEmpty(schemaLanguage)) {

			if (getVerbose()) {
				getLog()
						.info(
								"The schemaLanguage setting was not defined, assuming 'AUTODETECT'.");
			}
			return null;
		} else if ("AUTODETECT".equalsIgnoreCase(schemaLanguage))
			return null; // nothing, it is AUTDETECT by default.
		else if ("XMLSCHEMA".equalsIgnoreCase(schemaLanguage))
			return Language.XMLSCHEMA;
		else if ("DTD".equalsIgnoreCase(schemaLanguage))
			return Language.DTD;
		else if ("RELAXNG".equalsIgnoreCase(schemaLanguage))
			return Language.RELAXNG;
		else if ("RELAXNG_COMPACT".equalsIgnoreCase(schemaLanguage))
			return Language.RELAXNG_COMPACT;
		else if ("WSDL".equalsIgnoreCase(schemaLanguage))
			return Language.WSDL;
		else {
			throw new MojoExecutionException("Unknown schemaLanguage ["
					+ schemaLanguage + "]");
		}

	}

	/**
	 * Returns array of command line arguments for XJC. These arguments are
	 * based on the configured arguments (see {@link #getArgs()}) but also
	 * include episode arguments.
	 * 
	 * @return String array of XJC command line options.
	 * @throws MojoExecutionException
	 */

	protected String[] getArguments() throws MojoExecutionException {

		final List<String> arguments = new ArrayList<String>(getArgs());
		if (getEpisode() && getEpisodeFile() != null) {
			arguments.add("-episode");
			arguments.add(getEpisodeFile().getAbsolutePath());
		}

		for (final File episodeFile : getEpisodeFiles()) {
			arguments.add(episodeFile.getAbsolutePath());
		}
		return arguments.toArray(new String[arguments.size()]);
	}

	/**
	 * Creates an instance of catalog resolver.
	 * 
	 * @return
	 * @throws MojoExecutionException
	 */
	protected CatalogResolver getCatalogResolverInstance()
			throws MojoExecutionException {
		if (getCatalogResolver() == null) {
			return null;
		} else {

			try {
				final String catalogResolverClassName = getCatalogResolver()
						.trim();
				final Class<?> draftCatalogResolverClass = Thread
						.currentThread().getContextClassLoader().loadClass(
								catalogResolverClassName);
				if (!CatalogResolver.class
						.isAssignableFrom(draftCatalogResolverClass)) {
					throw new MojoExecutionException(
							"Specified catalog resolver class ["
									+ catalogResolver
									+ "] could not be casted to ["
									+ CatalogResolver.class + "].");
				} else {
					@SuppressWarnings("unchecked")
					final Class<? extends CatalogResolver> catalogResolverClass = (Class<? extends CatalogResolver>) draftCatalogResolverClass;
					final CatalogResolver catalogResolverInstance = catalogResolverClass
							.newInstance();
					return catalogResolverInstance;
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

	/**
	 * Scans given directory for files satisfying given inclusion/exclusion
	 * patterns.
	 * 
	 * @param directory
	 *            Directory to scan.
	 * @param includes
	 *            inclusion pattern.
	 * @param excludes
	 *            exclusion pattern.
	 * @param defaultExcludes
	 *            default exclusion flag.
	 * @return Files from the given directory which satisfy given patterns.
	 */
	protected List<File> getFiles(final File directory,
			final String[] includes, final String[] excludes,
			boolean defaultExcludes) throws MojoExecutionException {
		final DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(directory.getAbsoluteFile());
		scanner.setIncludes(includes);
		scanner.setExcludes(excludes);
		if (defaultExcludes) {
			scanner.addDefaultExcludes();
		}

		scanner.scan();

		final List<File> files = new ArrayList<File>();
		for (final String name : scanner.getIncludedFiles()) {
			try {
				files.add(new File(directory, name).getCanonicalFile());
			} catch (IOException ex) {
				throw new MojoExecutionException(
						"Unable to canonize the file [" + name + "].");
			}
		}

		return files;
	}

	private List<File> schemaFiles;

	protected List<File> createSchemaFiles() throws MojoExecutionException {
		return getFiles(getSchemaDirectory(), getSchemaIncludes(),
				getSchemaExcludes(), !getDisableDefaultExcludes());
	}

	public List<File> getSchemaFiles() {
		return schemaFiles;
	}

	protected List<InputSource> getGrammars() throws MojoExecutionException {
		final List<File> schemaFiles = getSchemaFiles();
		final List<InputSource> grammars = new ArrayList<InputSource>(
				schemaFiles.size());
		for (final File schemaFile : schemaFiles) {
			grammars.add(IOUtils.getInputSource(schemaFile));
		}
		return grammars;
	}

	protected List<File> createBindingFiles() throws MojoExecutionException {
		return getFiles(getBindingDirectory(), getBindingIncludes(),
				getBindingExcludes(), !getDisableDefaultExcludes());
	}

	private List<File> bindingFiles;

	public List<File> getBindingFiles() {
		return bindingFiles;
	}

	protected List<InputSource> getBindFiles() throws MojoExecutionException {
		final List<File> bindingFiles = getBindingFiles();
		final List<InputSource> bindFiles = new ArrayList<InputSource>(
				bindingFiles.size());
		for (final File bindingFile : bindingFiles) {
			bindFiles.add(IOUtils.getInputSource(bindingFile));
		}
		return bindFiles;

	}

	protected void setupDirectories() {

		final File generateDirectory = getGenerateDirectory();
		if (getRemoveOldOutput() && generateDirectory.exists()) {
			try {
				FileUtils.deleteDirectory(this.getGenerateDirectory());
			} catch (IOException ex) {
				getLog().warn(
						"Failed to remove old generateDirectory ["
								+ generateDirectory + "].", ex);
			}
		}

		// Create the destination path if it does not exist.
		if (generateDirectory != null && !generateDirectory.exists()) {
			generateDirectory.mkdirs();
		}

		final File episodeFile = getEpisodeFile();
		if (getEpisode() && episodeFile != null) {
			final File parentFile = episodeFile.getParentFile();
			parentFile.mkdirs();
		}
	}

	protected void setupFiles() throws MojoExecutionException {
		this.schemaFiles = createSchemaFiles();
		this.bindingFiles = createBindingFiles();
	}
}
