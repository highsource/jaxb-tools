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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import org.jvnet.jaxb2.maven2.util.LocaleUtils;
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

	protected List<URI> getSchemaUris() throws MojoExecutionException {
		final List<URI> schemaUris = new ArrayList<URI>(schemaFiles.size());
		final List<File> schemaFiles = getSchemaFiles();
		for (final File schemaFile : schemaFiles) {
			// try {
			final URI schema = schemaFile.toURI();
			schemaUris.add(schema);
			// } catch (MalformedURLException murlex) {
			// throw new MojoExecutionException(
			// MessageFormat.format(
			// "Could not create a schema URL for the schema file [{0}].",
			// schemaFile), murlex);
			// }
		}

		if (getSchemas() != null) {
			for (ResourceEntry resourceEntry : getSchemas()) {
				schemaUris.addAll(createResourceEntryUris(resourceEntry,
						getSchemaDirectory().getAbsolutePath(),
						getSchemaIncludes(), getSchemaExcludes()));
			}
		}
		return schemaUris;
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
			final Locale currentDefaultLocale = Locale.getDefault();
			try {
				final Locale locale = LocaleUtils.valueOf(getLocale());
				Locale.setDefault(locale);
				//
				doExecute();

			} finally {
				Locale.setDefault(currentDefaultLocale);
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

	protected ClassLoader createClassLoader(ClassLoader parent) {

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

		if (optionsConfiguration.getSchemas().isEmpty()) {
			getLog().info("Skipped XJC execution. No schemas to compile.");

		} else {

			final O options = getOptionsFactory().createOptions(
					optionsConfiguration);

			final boolean isUpToDate = isUpToDate();
			if (isUpToDate && getForceRegenerate()) {
				getLog().info(
						"Sources are up-to-date, but the [forceRegenerate] switch is turned on, XJC will be executed.");
			} else if (!isUpToDate) {
				getLog().info(
						"Sources are not up-to-date, XJC will be executed.");

			} else {
				getLog().info(
						"Generated sources are up-to-date, XJC will be skipped.");
				return;
			}
			setupDirectories();
			doExecute(options);
			final BuildContext buildContext = getBuildContext();
			getLog().debug(
					MessageFormat.format(
							"Refreshing generated directory [{0}].",
							getGenerateDirectory().getAbsolutePath()));
			buildContext.refresh(getGenerateDirectory());

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
		final File projectFile = getProject().getFile();
		if (projectFile != null) {
			dependsFiles.add(projectFile);
		}
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
		getLog().info("schemaUris (calculated):" + getSchemaUris());
		getLog().info("bindingFiles (calculated):" + getBindingFiles());
		getLog().info("bindingUris (calculated):" + getBindingUris());
		getLog().info(
				"xjcPluginArtifacts (resolved):" + getXjcPluginArtifacts());
		getLog().info("xjcPluginFiles (resolved):" + getXjcPluginFiles());
		getLog().info("xjcPluginURLs (resolved):" + getXjcPluginURLs());
		getLog().info("episodeArtifacts (resolved):" + getEpisodeArtifacts());
		getLog().info("episodeFiles (resolved):" + getEpisodeFiles());
	}

	protected List<URI> getBindingUris() throws MojoExecutionException {
		final List<File> bindingFiles = new LinkedList<File>();
		bindingFiles.addAll(getBindingFiles());

		for (final File episodeFile : getEpisodeFiles()) {
			getLog().debug(
					MessageFormat.format("Checking episode file [{0}].",
							episodeFile.getAbsolutePath()));
			if (episodeFile.isDirectory()) {
				final File episodeMetaInfFile = new File(episodeFile,
						"META-INF");
				if (episodeMetaInfFile.isDirectory()) {
					final File episodeBindingsFile = new File(
							episodeMetaInfFile, "sun-jaxb.episode");
					if (episodeBindingsFile.isFile()) {
						bindingFiles.add(episodeBindingsFile);
					}
				}
			}
		}

		final List<URI> bindingUris = new ArrayList<URI>(bindingFiles.size());
		for (final File bindingFile : bindingFiles) {
			URI uri;
			// try {
			uri = bindingFile.toURI();
			bindingUris.add(uri);
			// } catch (MalformedURLException murlex) {
			// throw new MojoExecutionException(
			// MessageFormat.format(
			// "Could not create a binding URL for the binding file [{0}].",
			// bindingFile), murlex);
			// }
		}
		if (getBindings() != null) {
			for (ResourceEntry resourceEntry : getBindings()) {
				bindingUris.addAll(createResourceEntryUris(resourceEntry,
						getBindingDirectory().getAbsolutePath(),
						getBindingIncludes(), getBindingExcludes()));
			}
		}

		if (getScanDependenciesForBindings()) {
			collectBindingUrisFromDependencies(bindingUris);
		}

		return bindingUris;

	}

	private void collectBindingUrisFromDependencies(List<URI> bindingUris)
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
					MessageFormat.format(
							"Scanning artifact [{0}] for JAXB binding files.",
							artifact));
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
						final URL resource = classLoader.getResource(entry
								.getName());
						try {
							bindingUris.add(resource.toURI());
						} catch (URISyntaxException urisex) {
							throw new MojoExecutionException(
									MessageFormat.format(
											"Could not create the URI of the binding file from [{0}]",
											resource), urisex);
						}
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
		final CatalogManager catalogManager = new CatalogManager();
		catalogManager.setIgnoreMissingProperties(true);
		catalogManager.setUseStaticCatalog(false);
		// TODO Logging
		if (getLog().isDebugEnabled()) {
			catalogManager.setVerbosity(Integer.MAX_VALUE);
		}
		if (getCatalogResolver() == null) {
			// System.out.println("Creating a new maven catalog resolver.");
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
								"File ["
										+ dependsFile.getAbsolutePath()
										+ "] might have been changed since the last build.");
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
							.format("Depends timestamp [{0,date,yyyy-MM-dd HH:mm:ss.SSS}], produces timestamp [{1,date,yyyy-MM-dd HH:mm:ss.SSS}].",
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
			if (episodeFile.isFile()) {
				arguments.add(episodeFile.getAbsolutePath());
			}
		}
		return arguments;
	}

	public OptionsConfiguration createOptionsConfiguration()
			throws MojoExecutionException {
		final OptionsConfiguration optionsConfiguration = new OptionsConfiguration(
				getEncoding(), getSchemaLanguage(), getSchemaUris(),
				getBindingUris(), getCatalogUris(), createCatalogResolver(),
				getGeneratePackage(), getGenerateDirectory(), getReadOnly(),
				getPackageLevelAnnotations(), getNoFileHeader(),
				getEnableIntrospection(), getDisableXmlSecurity(),
				getAccessExternalSchema(), getAccessExternalDTD(),
				getContentForWildcard(), getExtension(), getStrict(),
				getVerbose(), getDebug(), getArguments(), getXjcPluginURLs(),
				getSpecVersion());
		return optionsConfiguration;
	}
}
