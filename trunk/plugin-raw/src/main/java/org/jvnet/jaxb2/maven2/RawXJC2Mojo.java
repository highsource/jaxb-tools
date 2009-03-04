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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.util.FileUtils;
import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoPhase;
import org.jvnet.jaxb2.maven2.util.ArtifactUtils;
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
import com.sun.tools.xjc.api.SpecVersion;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

/**
 * Maven JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@MojoGoal("generate")
@MojoPhase("generate-sources")
public class RawXJC2Mojo extends AbstractXJC2Mojo {

	private Collection<Artifact> xjcPluginArtifacts;

	private Collection<File> xjcPluginFiles;

	private Collection<URL> xjcPluginURLs;

	public Collection<Artifact> getXjcPluginArtifacts() {
		return xjcPluginArtifacts;
	}

	public Collection<File> getXjcPluginFiles() {
		return xjcPluginFiles;
	}

	public Collection<URL> getXjcPluginURLs() {
		return xjcPluginURLs;
	}

	private Collection<Artifact> episodeArtifacts;

	private Collection<File> episodeFiles;

	public Collection<Artifact> getEpisodeArtifacts() {
		return episodeArtifacts;
	}

	public Collection<File> getEpisodeFiles() {
		return episodeFiles;
	}

	private List<File> schemaFiles;

	public List<File> getSchemaFiles() {
		return schemaFiles;
	}

	private List<File> bindingFiles;

	public List<File> getBindingFiles() {
		return bindingFiles;
	}

	private List<File> dependsFiles;

	public List<File> getDependsFiles() {
		return dependsFiles;
	}

	private List<File> producesFiles;

	public List<File> getProducesFiles() {
		return producesFiles;
	}

	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 */
	public void execute() throws MojoExecutionException {

		injectDependencyDefaults();
		resolveArtifacts();

		// Install project dependencies into classloader's class path
		// and execute xjc2.
		final ClassLoader currentClassLoader = Thread.currentThread()
				.getContextClassLoader();
		final ClassLoader classLoader = createClassLoader(currentClassLoader);
		Thread.currentThread().setContextClassLoader(classLoader);
		try {
			doExecute();

		} finally {
			// Set back the old classloader
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * *************************************************************************
	 * *
	 */

	protected void injectDependencyDefaults() {
		injectDependencyDefaults(getPlugins());
		injectDependencyDefaults(getEpisodes());
	}

	@SuppressWarnings("unchecked")
	protected void injectDependencyDefaults(Dependency[] dependencies) {
		if (dependencies != null) {
			final Map<String, Dependency> dependencyMap = new TreeMap<String, Dependency>();
			for (final Dependency dependency : dependencies) {
				dependencyMap.put(dependency.getManagementKey(), dependency);
			}

			final DependencyManagement dependencyManagement = getProject()
					.getDependencyManagement();

			if (dependencyManagement != null) {
				merge(dependencyMap, dependencyManagement.getDependencies());
			}
			if (getProject().getDependencies() != null) {
				merge(dependencyMap, getProject().getDependencies());
			}
		}
	}

	private void merge(final Map<String, Dependency> dependencyMap,
			final List<Dependency> managedDependencies) {
		for (final Dependency managedDependency : managedDependencies) {
			final String key = managedDependency.getManagementKey();
			final Dependency dependency = (Dependency) dependencyMap.get(key);
			if (dependency != null) {
				ArtifactUtils.mergeDependencyWithDefaults(dependency,
						managedDependency);
			}
		}
	}

	protected void resolveArtifacts() throws MojoExecutionException {
		try {

			resolveXJCPluginArtifacts();
			resolveEpisodeArtifacts();
		} catch (ArtifactResolutionException arex) {
			throw new MojoExecutionException("Could not resolve the artifact.",
					arex);
		} catch (ArtifactNotFoundException anfex) {
			throw new MojoExecutionException("Artifact not found.", anfex);
		} catch (InvalidDependencyVersionException idvex) {
			throw new MojoExecutionException("Invalid dependency version.",
					idvex);
		}
	}

	protected void resolveXJCPluginArtifacts()
			throws ArtifactResolutionException, ArtifactNotFoundException,
			InvalidDependencyVersionException {

		this.xjcPluginArtifacts = ArtifactUtils.resolveTransitively(
				getArtifactFactory(), getArtifactResolver(),
				getLocalRepository(), getArtifactMetadataSource(),
				getPlugins(), getProject());
		this.xjcPluginFiles = ArtifactUtils.getFiles(this.xjcPluginArtifacts);
		this.xjcPluginURLs = CollectionUtils.apply(this.xjcPluginFiles,
				IOUtils.GET_URL);
	}

	protected void resolveEpisodeArtifacts()
			throws ArtifactResolutionException, ArtifactNotFoundException,
			InvalidDependencyVersionException {
		this.episodeArtifacts = ArtifactUtils.resolve(getArtifactFactory(),
				getArtifactResolver(), getLocalRepository(),
				getArtifactMetadataSource(), getEpisodes(), getProject());
		this.episodeFiles = ArtifactUtils.getFiles(this.episodeArtifacts);
	}

	/**
	 * @param parent
	 *            the returned classLoader will be a descendant of this one.
	 * @return a context class loader with a classPath containing the project
	 *         dependencies.
	 * @throws MojoExecutionException
	 */
	protected ClassLoader createClassLoader(ClassLoader parent)
			throws MojoExecutionException {

		final Collection<URL> xjcPluginURLs = getXjcPluginURLs();

		return new ParentFirstClassLoader(xjcPluginURLs
				.toArray(new URL[xjcPluginURLs.size()]), parent);
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
		setupSchemaFiles();
		setupBindingFiles();
		setupDependsFiles();
		setupProducesFiles();
	}

	protected void setupSchemaFiles() throws MojoExecutionException {
		try {
			this.schemaFiles = IOUtils.scanDirectoryForFiles(
					getSchemaDirectory(), getSchemaIncludes(),
					getSchemaExcludes(), !getDisableDefaultExcludes());
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not setup schema files.",
					ioex);
		}
	}

	protected void setupBindingFiles() throws MojoExecutionException {
		try {
			this.bindingFiles = IOUtils.scanDirectoryForFiles(
					getBindingDirectory(), getBindingIncludes(),
					getBindingExcludes(), !getDisableDefaultExcludes());
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not setup schema files.",
					ioex);
		}
	}

	protected void setupDependsFiles() {
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
		this.dependsFiles = dependsFiles;
	}

	protected void setupProducesFiles() throws MojoExecutionException {
		try {
			this.producesFiles = IOUtils.scanDirectoryForFiles(
					getGenerateDirectory(), new String[] { "**/*.*",
							"**/*.java", "**/bgm.ser", "**/jaxb.properties" },
					new String[0], false);
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not setup produced files.",
					ioex);
		}
	}

	/**
	 * Log the configuration settings. Shown when exception thrown or when
	 * verbose is true.
	 */
	protected void logConfiguration() throws MojoExecutionException {
		super.logConfiguration();
		getLog().info("schemaFiles (calculated):" + getSchemaFiles());
		getLog().info("bindingFiles (calculated):" + getBindingFiles());
		getLog().info(
				"xjcPluginArtifacts (resolved):" + getXjcPluginArtifacts());
		getLog().info("xjcPluginFiles (resolved):" + getXjcPluginFiles());
		getLog().info("xjcPluginURLs (resolved):" + getXjcPluginURLs());
		getLog().info("episodeArtifacts (resolved):" + getEpisodeArtifacts());
		getLog().info("episodeFiles (resolved):" + getEpisodeArtifacts());
	}

	/**
	 * *************************************************************************
	 * *
	 */

	/**
	 * Creates and initializes an instance of XJC options.
	 * 
	 */
	protected Options setupOptions() throws MojoExecutionException {
		final Options options = new Options();

		options.verbose = this.getVerbose();
		options.classpaths.addAll(getXjcPluginURLs());
		options.debugMode = this.getDebug();
		final SpecVersion specVersion = SpecVersion.parse(getSpecVersion());
		options.target = specVersion == null ? SpecVersion.LATEST : specVersion;

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

	protected List<InputSource> getGrammars() throws MojoExecutionException {
		final List<File> schemaFiles = getSchemaFiles();
		final List<InputSource> grammars = new ArrayList<InputSource>(
				schemaFiles.size());
		for (final File schemaFile : schemaFiles) {
			grammars.add(IOUtils.getInputSource(schemaFile));
		}
		return grammars;
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
			getLog().info(
					"Checking up-to-date produces [" + producesFiles + "].");

		final Long dependsTimestamp = CollectionUtils.bestValue(dependsFiles,
				IOUtils.LAST_MODIFIED, CollectionUtils.<Long> gt());
		final Long producesTimestamp = CollectionUtils.bestValue(producesFiles,
				IOUtils.LAST_MODIFIED, CollectionUtils.<Long> lt());

		if (getVerbose())
			getLog().info(
					"Depends timestamp [" + dependsTimestamp
							+ "], produces timestamp [" + producesTimestamp
							+ "].");

		return producesTimestamp != null
				&& CollectionUtils.<Long> lt().compare(dependsTimestamp,
						producesTimestamp) > 0;
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

}
