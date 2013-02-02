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
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.util.FileUtils;
import org.jvnet.jaxb2.maven2.resolver.tools.MavenCatalogResolver;
import org.jvnet.jaxb2.maven2.util.ArtifactUtils;
import org.jvnet.jaxb2.maven2.util.CollectionUtils;
import org.jvnet.jaxb2.maven2.util.IOUtils;
import org.sonatype.plexus.build.incremental.BuildContext;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

/**
 * Maven JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
public abstract class RawXJC2Mojo<O> extends AbstractXJC2Mojo<O> {

	private Collection<Artifact> xjcPluginArtifacts;

	private Collection<File> xjcPluginFiles;

	private List<URL> xjcPluginURLs;

	public Collection<Artifact> getXjcPluginArtifacts() {
		return xjcPluginArtifacts;
	}

	public Collection<File> getXjcPluginFiles() {
		return xjcPluginFiles;
	}

	public List<URL> getXjcPluginURLs() {
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

	protected List<URL> getSchemaUrls() throws MojoExecutionException {
		final List<URL> schemaUrls = new ArrayList<URL>(schemaFiles.size());
		final List<File> schemaFiles = getSchemaFiles();
		for (final File schemaFile : schemaFiles) {
			try {
				final URL schema = schemaFile.toURI().toURL();
				schemaUrls.add(schema);
			} catch (MalformedURLException murlex) {
				throw new MojoExecutionException(
						MessageFormat.format(
								"Could not create a schema URL for the schema file [{0}].",
								schemaFile), murlex);
			}
		}

		if (getSchemas() != null) {
			for (ResourceEntry resourceEntry : getSchemas()) {
				schemaUrls.addAll(createResourceEntryUrls(resourceEntry,
						getSchemaDirectory().getAbsolutePath(),
						getSchemaIncludes(), getSchemaExcludes()));
			}
		}
		return schemaUrls;
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

	private static final Object lock = new Object();

	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 */
	public void execute() throws MojoExecutionException {
		synchronized (lock) {
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
				Thread.currentThread()
						.setContextClassLoader(currentClassLoader);
			}
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
				if (dependency.getScope() == null) {
					dependency.setScope(Artifact.SCOPE_RUNTIME);
				}
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
		this.episodeArtifacts = new LinkedHashSet<Artifact>();
		{
			final Collection<Artifact> episodeArtifacts = ArtifactUtils
					.resolve(getArtifactFactory(), getArtifactResolver(),
							getLocalRepository(), getArtifactMetadataSource(),
							getEpisodes(), getProject());
			this.episodeArtifacts.addAll(episodeArtifacts);
		}
		{
			if (getUseDependenciesAsEpisodes()) {
				@SuppressWarnings("unchecked")
				final Collection<Artifact> projectArtifacts = getProject()
						.getArtifacts();
				final ArtifactFilter filter = new ScopeArtifactFilter(
						DefaultArtifact.SCOPE_COMPILE);
				for (Artifact artifact : projectArtifacts) {
					if (filter.include(artifact)) {
						this.episodeArtifacts.add(artifact);
					}
				}
			}
		}
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

		return new ParentFirstClassLoader(
				xjcPluginURLs.toArray(new URL[xjcPluginURLs.size()]), parent);
	}

	protected void doExecute() throws MojoExecutionException {
		setupLogging();
		if (getVerbose())
			getLog().info("Started execution.");
		setupMavenPaths();
		setupFiles();
		if (getVerbose()) {
			logConfiguration();
		}

		final OptionsConfiguration optionsConfiguration = createOptionsConfiguration();

		if (getVerbose()) {
			getLog().info("optionsConfiguration:" + optionsConfiguration);
		}

		if (optionsConfiguration.getGrammars().isEmpty()) {
			getLog().info("Skipped XJC execution. No schemas to compile.");

		} else {

			final O options = getOptionsFactory().createOptions(
					optionsConfiguration);

			if (!this.getForceRegenerate() && isUpToDate()) {
				getLog().info(
						"Skipped XJC execution. Generated sources were up-to-date.");
				return;
			}
			setupDirectories();
			doExecute(options);
			final BuildContext buildContext = getBuildContext();
			if (buildContext != null) {
				buildContext.refresh(getGenerateDirectory());
			}

		}

		if (getVerbose()) {
			getLog().info("Finished execution.");
		}
	}

	public abstract void doExecute(O options) throws MojoExecutionException;

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
			System.setProperty("com.sun.tools.xjc.Options.findServices", "true");
		}

	}

	/**
	 * Augments Maven paths with generated resources.
	 */
	protected void setupMavenPaths() {

		if (getAddCompileSourceRoot()) {
			getProject().addCompileSourceRoot(getGenerateDirectory().getPath());
		}
		if (getAddTestCompileSourceRoot()) {
			getProject().addTestCompileSourceRoot(
					getGenerateDirectory().getPath());
		}
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
				if (getAddCompileSourceRoot()) {
					getProject().addResource(resource);
				}
				if (getAddTestCompileSourceRoot()) {
					getProject().addTestResource(resource);

				}
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
			final File schemaDirectory = getSchemaDirectory();
			if (schemaDirectory == null || !schemaDirectory.exists()) {
				this.schemaFiles = Collections.emptyList();
			} else if (schemaDirectory.isDirectory()) {
				this.schemaFiles = IOUtils.scanDirectoryForFiles(
						getBuildContext(), schemaDirectory,
						getSchemaIncludes(), getSchemaExcludes(),
						!getDisableDefaultExcludes());

			} else {
				this.schemaFiles = Collections.emptyList();
				getLog().warn(
						MessageFormat.format(
								"Schema directory [{0}] is not a directory.",
								schemaDirectory.getPath()));
			}
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not setup schema files.",
					ioex);
		}
	}

	protected void setupBindingFiles() throws MojoExecutionException {
		try {
			final File bindingDirectory = getBindingDirectory();
			if (bindingDirectory == null || !bindingDirectory.exists()) {
				this.bindingFiles = Collections.emptyList();
			} else if (bindingDirectory.isDirectory()) {
				this.bindingFiles = IOUtils.scanDirectoryForFiles(
						getBuildContext(), bindingDirectory,
						getBindingIncludes(), getBindingExcludes(),
						!getDisableDefaultExcludes());
			} else {
				this.bindingFiles = Collections.emptyList();
				getLog().warn(
						MessageFormat.format(
								"Binding directory [{0}] is not a directory.",
								bindingDirectory.getPath()));
			}
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not setup binding files.",
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
					getBuildContext(), getGenerateDirectory(), getProduces(),
					new String[0], !getDisableDefaultExcludes());
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
		getLog().info("episodeFiles (resolved):" + getEpisodeFiles());
	}

	/**
	 * *************************************************************************
	 * *
	 */

	protected List<URL> getBindingUrls() throws MojoExecutionException {
		final List<File> bindingFiles = getBindingFiles();
		final List<URL> bindingUrls = new ArrayList<URL>(bindingFiles.size());
		for (final File bindingFile : bindingFiles) {
			URL url;
			try {
				url = bindingFile.toURI().toURL();
				bindingUrls.add(url);
			} catch (MalformedURLException murlex) {
				throw new MojoExecutionException(
						MessageFormat.format(
								"Could not create a binding URL for the binding file [{0}].",
								bindingFile), murlex);
			}
		}
		if (getBindings() != null) {
			for (ResourceEntry resourceEntry : getBindings()) {
				bindingUrls.addAll(createResourceEntryUrls(resourceEntry,
						getBindingDirectory().getAbsolutePath(),
						getBindingIncludes(), getBindingExcludes()));
			}
		}

		if (getScanDependenciesForBindings()) {
			collectBindingUrlsFromDependencies(bindingUrls);
		}

		return bindingUrls;

	}

	private void collectBindingUrlsFromDependencies(List<URL> bindingUrls)
			throws MojoExecutionException {
		@SuppressWarnings("unchecked")
		final Collection<Artifact> projectArtifacts = getProject()
				.getArtifacts();
		final List<Artifact> compileScopeArtifacts = new ArrayList<Artifact>(
				projectArtifacts.size());
		final ArtifactFilter filter = new ScopeArtifactFilter(
				DefaultArtifact.SCOPE_COMPILE);
		for (Artifact artifact : projectArtifacts) {
			if (filter.include(artifact)) {
				compileScopeArtifacts.add(artifact);
			}
		}

		for (Artifact artifact : compileScopeArtifacts) {
			getLog().debug(
					"Scanning artifact [" + artifact
							+ "] for JAXB binding files.");
			final File file = artifact.getFile();
			ClassLoader classLoader = null;
			try {
				classLoader = new URLClassLoader(new URL[] { file.toURI()
						.toURL() });
			} catch (IOException ioex) {
				throw new MojoExecutionException(
						"Unable to create a classloader for the artifact file ["
								+ file.getAbsolutePath() + "].", ioex);
			}
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(file);
				final Enumeration<JarEntry> jarFileEntries = jarFile.entries();
				while (jarFileEntries.hasMoreElements()) {
					JarEntry entry = jarFileEntries.nextElement();
					if (entry.getName().endsWith(".xjb")) {
						bindingUrls
								.add(classLoader.getResource(entry.getName()));
					}
				}
			} catch (IOException ioex) {
				throw new MojoExecutionException(
						"Unable to read the artifact JAR file ["
								+ file.getAbsolutePath() + "].", ioex);
			} finally {
				if (jarFile != null) {
					try {
						jarFile.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
	}

	/**
	 * Creates an instance of catalog resolver.
	 * 
	 * @return
	 * @throws MojoExecutionException
	 */
	protected CatalogResolver createCatalogResolver()
			throws MojoExecutionException {
		final CatalogManager catalogManager = CatalogManager.getStaticManager();
		catalogManager.setIgnoreMissingProperties(true);
		if (getCatalogResolver() == null) {
			return new MavenCatalogResolver(catalogManager, this);
		} else {
			try {
				final String catalogResolverClassName = getCatalogResolver()
						.trim();
				final Class<?> draftCatalogResolverClass = Thread
						.currentThread().getContextClassLoader()
						.loadClass(catalogResolverClassName);
				if (!CatalogResolver.class
						.isAssignableFrom(draftCatalogResolverClass)) {
					throw new MojoExecutionException(
							MessageFormat
									.format("Specified catalog resolver class [{0}] could not be casted to [{1}].",
											catalogResolver,
											CatalogResolver.class));
				} else {
					@SuppressWarnings("unchecked")
					final Class<? extends CatalogResolver> catalogResolverClass = (Class<? extends CatalogResolver>) draftCatalogResolverClass;
					final CatalogResolver catalogResolverInstance = catalogResolverClass
							.newInstance();
					return catalogResolverInstance;
				}
			} catch (ClassNotFoundException cnfex) {
				throw new MojoExecutionException(
						MessageFormat.format(
								"Could not find specified catalog resolver class [{0}].",
								catalogResolver), cnfex);
			} catch (InstantiationException iex) {
				throw new MojoExecutionException(MessageFormat.format(
						"Could not instantiate catalog resolver class [{0}].",
						catalogResolver), iex);
			} catch (IllegalAccessException iaex) {
				throw new MojoExecutionException(MessageFormat.format(
						"Could not instantiate catalog resolver class [{0}].",
						catalogResolver), iaex);
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

		boolean delta = false;
		{
			for (File dependsFile : dependsFiles) {
				if (getBuildContext().hasDelta(dependsFile)) {
					if (getVerbose()) {
						getLog().info(
								"File [" + dependsFile.getAbsolutePath()
										+ "] was changed since the last build.");
					}
					delta = true;
				}
			}
		}
		if (!delta) {
			if (getVerbose()) {
				getLog().info("No files were changed since the last build.");
			}
			return true;
		}
		if (getVerbose()) {
			getLog().info(
					MessageFormat.format("Checking up-to-date depends [{0}].",
							dependsFiles));
		}
		if (getVerbose()) {
			getLog().info(
					MessageFormat.format("Checking up-to-date produces [{0}].",
							producesFiles));
		}

		final Long dependsTimestamp = CollectionUtils.bestValue(dependsFiles,
				IOUtils.LAST_MODIFIED, CollectionUtils.<Long> gt());
		final Long producesTimestamp = CollectionUtils.bestValue(producesFiles,
				IOUtils.LAST_MODIFIED, CollectionUtils.<Long> lt());

		if (getVerbose()) {
			getLog().info(
					MessageFormat
							.format("Depends timestamp [{0}], produces timestamp [{1}].",
									dependsTimestamp, producesTimestamp));
		}
		return producesTimestamp != null
				&& CollectionUtils.<Long> lt().compare(dependsTimestamp,
						producesTimestamp) > 0;
	}

	/**
	 * Returns array of command line arguments for XJC. These arguments are
	 * based on the configured arguments (see {@link #getArgs()}) but also
	 * include episode arguments.
	 * 
	 * @return String array of XJC command line options.
	 * @throws MojoExecutionException
	 */

	protected List<String> getArguments() throws MojoExecutionException {

		final List<String> arguments = new ArrayList<String>(getArgs());
		if (getEpisode() && getEpisodeFile() != null) {
			arguments.add("-episode");
			arguments.add(getEpisodeFile().getAbsolutePath());
		}

		if (getMarkGenerated()) {
			arguments.add("-mark-generated");
		}

		for (final File episodeFile : getEpisodeFiles()) {
			arguments.add(episodeFile.getAbsolutePath());
		}
		return arguments;
	}

	public OptionsConfiguration createOptionsConfiguration()
			throws MojoExecutionException {
		final OptionsConfiguration optionsConfiguration = new OptionsConfiguration(
				getEncoding(), getSchemaLanguage(), getSchemaUrls(),
				getBindingUrls(), getCatalogUrls(), createCatalogResolver(),
				getGeneratePackage(), getGenerateDirectory(), getReadOnly(),
				getExtension(), getStrict(), getVerbose(), getDebug(),
				getArguments(), getXjcPluginURLs(), getSpecVersion());
		return optionsConfiguration;
	}
}
