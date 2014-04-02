package org.jvnet.jaxb2.maven2;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.FileSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.apache.maven.project.artifact.MavenMetadataSource;
import org.jvnet.jaxb2.maven2.util.ArtifactUtils;
import org.jvnet.jaxb2.maven2.util.IOUtils;
import org.sonatype.plexus.build.incremental.BuildContext;

public abstract class AbstractXJC2Mojo<O> extends AbstractMojo implements
		DependencyResourceResolver {

	/**
	 * Encoding for the generated sources, defaults to
	 * ${project.build.sourceEncoding}.
	 */
	@Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}")
	private String encoding;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Type of input schema language. One of: DTD, XMLSCHEMA, RELAXNG,
	 * RELAXNG_COMPACT, WSDL, AUTODETECT. If unspecified, it is assumed
	 * AUTODETECT.
	 */
	@Parameter(property = "maven.xjc2.schemaLanguage")
	private String schemaLanguage;

	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public void setSchemaLanguage(String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	/**
	 * The source directory containing *.xsd schema files. Notice that binding
	 * files are searched by default in this directory.
	 */
	@Parameter(defaultValue = "src/main/resources", property = "maven.xjc2.schemaDirectory", required = true)
	private File schemaDirectory;

	public File getSchemaDirectory() {
		return schemaDirectory;
	}

	public void setSchemaDirectory(File schemaDirectory) {
		this.schemaDirectory = schemaDirectory;
	}

	/**
	 * <p>
	 * A list of regular expression file search patterns to specify the schemas
	 * to be processed. Searching is based from the root of
	 * <code>schemaDirectory</code>.
	 * </p>
	 * <p>
	 * If left undefined, then all <code>*.xsd</code> files in
	 * <code>schemaDirectory</code> will be processed.
	 * </p>
	 */
	@Parameter
	private String[] schemaIncludes = new String[] { "*.xsd" };

	public String[] getSchemaIncludes() {
		return schemaIncludes;
	}

	public void setSchemaIncludes(String[] schemaIncludes) {
		this.schemaIncludes = schemaIncludes;
	}

	/**
	 * A list of regular expression file search patterns to specify the schemas
	 * to be excluded from the <code>schemaIncludes</code> list. Searching is
	 * based from the root of schemaDirectory.
	 */
	@Parameter
	private String[] schemaExcludes;

	public String[] getSchemaExcludes() {
		return schemaExcludes;
	}

	public void setSchemaExcludes(String[] schemaExcludes) {
		this.schemaExcludes = schemaExcludes;
	}

	/**
	 * A list of schema resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@Parameter
	private ResourceEntry[] schemas = new ResourceEntry[0];

	public ResourceEntry[] getSchemas() {
		return schemas;
	}

	public void setSchemas(ResourceEntry[] schemas) {
		this.schemas = schemas;
	}

	/**
	 * <p>
	 * The source directory containing the *.xjb binding files.
	 * </p>
	 * <p>
	 * If left undefined, then the <code>schemaDirectory</code> is assumed.
	 * </p>
	 */
	@Parameter(property = "maven.xjc2.bindingDirectory")
	private File bindingDirectory;

	public void setBindingDirectory(File bindingDirectory) {
		this.bindingDirectory = bindingDirectory;
	}

	public File getBindingDirectory() {
		return bindingDirectory != null ? bindingDirectory
				: getSchemaDirectory();
	}

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
	@Parameter
	private String[] bindingIncludes = new String[] { "*.xjb" };

	public String[] getBindingIncludes() {
		return bindingIncludes;
	}

	public void setBindingIncludes(String[] bindingIncludes) {
		this.bindingIncludes = bindingIncludes;
	}

	/**
	 * A list of regular expression file search patterns to specify the binding
	 * files to be excluded from the <code>bindingIncludes</code>. Searching is
	 * based from the root of bindingDirectory.
	 */
	@Parameter
	private String[] bindingExcludes;

	public String[] getBindingExcludes() {
		return bindingExcludes;
	}

	public void setBindingExcludes(String[] bindingExcludes) {
		this.bindingExcludes = bindingExcludes;
	}

	/**
	 * A list of binding resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@Parameter
	private ResourceEntry[] bindings = new ResourceEntry[0];

	public ResourceEntry[] getBindings() {
		return bindings;
	}

	public void setBindings(ResourceEntry[] bindings) {
		this.bindings = bindings;
	}

	/**
	 * If 'true', maven's default exludes are NOT added to all the excludes
	 * lists.
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.disableDefaultExcludes")
	private boolean disableDefaultExcludes;

	public boolean getDisableDefaultExcludes() {
		return disableDefaultExcludes;
	}

	public void setDisableDefaultExcludes(boolean disableDefaultExcludes) {
		this.disableDefaultExcludes = disableDefaultExcludes;
	}

	/**
	 * Specify the catalog file to resolve external entity references (xjc's
	 * -catalog option) </p>
	 * <p>
	 * Support TR9401, XCatalog, and OASIS XML Catalog format. See the
	 * catalog-resolver sample and this article for details.
	 * </p>
	 */
	@Parameter(property = "maven.xjc2.catalog")
	private File catalog;

	public File getCatalog() {
		return catalog;
	}

	public void setCatalog(File catalog) {
		this.catalog = catalog;
	}

	/**
	 * A list of catalog resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@Parameter
	private ResourceEntry[] catalogs = new ResourceEntry[0];

	public ResourceEntry[] getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(ResourceEntry[] catalogs) {
		this.catalogs = catalogs;
	}

	protected List<URL> getCatalogUrls() throws MojoExecutionException {
		final File catalog = getCatalog();
		final ResourceEntry[] catalogs = getCatalogs();
		final List<URL> catalogUrls = new ArrayList<URL>((catalog == null ? 0
				: 1) + catalogs.length);
		if (catalog != null) {
			try {
				catalogUrls.add(getCatalog().toURI().toURL());
			} catch (MalformedURLException murlex) {
				throw new MojoExecutionException(
						MessageFormat.format(
								"Could not create a catalog URL for the catalog file [{0}].",
								catalog), murlex);

			}
		}
		for (ResourceEntry resourceEntry : catalogs) {
			catalogUrls.addAll(createResourceEntryUrls(resourceEntry,
					getSchemaDirectory().getAbsolutePath(),
					getSchemaIncludes(), getSchemaExcludes()));
		}
		return catalogUrls;
	}

	/**
	 * Provides the class name of the catalog resolver.
	 */
	@Parameter(property = "maven.xjc2.catalogResolver")
	protected String catalogResolver = null;

	public String getCatalogResolver() {
		return catalogResolver;
	}

	public void setCatalogResolver(String catalogResolver) {
		this.catalogResolver = catalogResolver;
	}

	/**
	 * <p>
	 * The generated classes will all be placed under this Java package (xjc's
	 * -p option), unless otherwise specified in the schemas.
	 * </p>
	 * <p>
	 * If left unspecified, the package will be derived from the schemas only.
	 * </p>
	 */
	@Parameter(property = "maven.xjc2.generatePackage")
	private String generatePackage;

	public String getGeneratePackage() {
		return generatePackage;
	}

	public void setGeneratePackage(String generatePackage) {
		this.generatePackage = generatePackage;
	}

	/**
	 * <p>
	 * Generated code will be written under this directory.
	 * </p>
	 * <p>
	 * For instance, if you specify <code>generateDirectory="doe/ray"</code> and
	 * <code>generatePackage="org.here"</code>, then files are generated to
	 * <code>doe/ray/org/here</code>.
	 * </p>
	 */
	@Parameter(defaultValue = "${project.build.directory}/generated-sources/xjc", property = "maven.xjc2.generateDirectory", required = true)
	private File generateDirectory;

	public File getGenerateDirectory() {
		return generateDirectory;
	}

	public void setGenerateDirectory(File generateDirectory) {
		this.generateDirectory = generateDirectory;

		if (getEpisodeFile() == null) {
			final File episodeFile = new File(getGenerateDirectory(),
					"META-INF" + File.separator + "sun-jaxb.episode");
			setEpisodeFile(episodeFile);
		}
	}

	/**
	 * If set to true (default), adds target directory as a compile source root
	 * of this Maven project.
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.addCompileSourceRoot", required = false)
	private boolean addCompileSourceRoot = true;

	public boolean getAddCompileSourceRoot() {
		return addCompileSourceRoot;
	}

	public void setAddCompileSourceRoot(boolean addCompileSourceRoot) {
		this.addCompileSourceRoot = addCompileSourceRoot;
	}

	/**
	 * If set to true, adds target directory as a test compile source root of
	 * this Maven project. Default value is false.
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.addTestCompileSourceRoot", required = false)
	private boolean addTestCompileSourceRoot = false;

	public boolean getAddTestCompileSourceRoot() {
		return addTestCompileSourceRoot;
	}

	public void setAddTestCompileSourceRoot(boolean addTestCompileSourceRoot) {
		this.addTestCompileSourceRoot = addTestCompileSourceRoot;
	}

	/**
	 * If 'true', the generated Java source files are set as read-only (xjc's
	 * -readOnly option).
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.readOnly")
	private boolean readOnly;

	public boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * If 'false', suppresses generation of package level annotations
	 * (package-info.java), xjc's -npa option.
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.packageLevelAnnotations")
	private boolean packageLevelAnnotations = true;

	public boolean getPackageLevelAnnotations() {
		return packageLevelAnnotations;
	}

	public void setPackageLevelAnnotations(boolean packageLevelAnnotations) {
		this.packageLevelAnnotations = packageLevelAnnotations;
	}

	/**
	 * If 'true', suppresses generation of a file header with timestamp, xjc's
	 * -no-header option.
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.noFileHeader")
	private boolean noFileHeader = false;

	public boolean getNoFileHeader() {
		return noFileHeader;
	}

	public void setNoFileHeader(boolean noFileHeader) {
		this.noFileHeader = noFileHeader;
	}

	/**
	 * If 'true', enables correct generation of Boolean getters/setters to
	 * enable Bean Introspection apis; xjc's -enableIntrospection option.
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.enableIntrospection")
	private boolean enableIntrospection = false;

	public boolean getEnableIntrospection() {
		return enableIntrospection;
	}

	public void setEnableIntrospection(boolean enableIntrospection) {
		this.enableIntrospection = enableIntrospection;
	}

	/**
	 * If 'true', disables XML security features when parsing XML documents;
	 * xjc's -disableXmlSecurity option.
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.disableXmlSecurity")
	private boolean disableXmlSecurity = true;

	public boolean getDisableXmlSecurity() {
		return disableXmlSecurity;
	}

	public void setDisableXmlSecurity(boolean disableXmlSecurity) {
		this.disableXmlSecurity = disableXmlSecurity;
	}

	/**
	 * Restrict access to the protocols specified for external reference set by
	 * the schemaLocation attribute, Import and Include element. Value: a list
	 * of protocols separated by comma. A protocol is the scheme portion of a
	 * {@link java.net.URI}, or in the case of the JAR protocol, "jar" plus the
	 * scheme portion separated by colon. The keyword "all" to grant permission
	 * to all protocols.
	 */
	private String accessExternalSchema = "all";
	
	public String getAccessExternalSchema() {
		return accessExternalSchema;
	}
	
	public void setAccessExternalSchema(String accessExternalSchema) {
		this.accessExternalSchema = accessExternalSchema;
	}

	/**
	 * Restricts access to external DTDs and external Entity References to the
	 * protocols specified. Value: a list of protocols separated by comma. A
	 * protocol is the scheme portion of a {@link java.net.URI}, or in the case
	 * of the JAR protocol, "jar" plus the scheme portion separated by colon.
	 * The keyword "all" to grant permission to all protocols.
	 */
	@Parameter(defaultValue = "all", property = "maven.xjc2.accessExternalDTD")
	private String accessExternalDTD = "all";

	public String getAccessExternalDTD() {
		return accessExternalDTD;
	}

	public void setAccessExternalDTD(String accessExternalDTD) {
		this.accessExternalDTD = accessExternalDTD;
	}

	/**
	 * If 'true', generates content property for types with multiple xs:any
	 * derived elements; xjc's -contentForWildcard option.
	 */
	private boolean contentForWildcard;

	public boolean getContentForWildcard() {
		return contentForWildcard;
	}

	public void setContentForWildcard(boolean contentForWildcard) {
		this.contentForWildcard = contentForWildcard;
	}

	/**
	 * If 'true', the XJC binding compiler will run in the extension mode (xjc's
	 * -extension option). Otherwise, it will run in the strict conformance
	 * mode.
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.extension")
	private boolean extension;

	public boolean getExtension() {
		return extension;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	/**
	 * If 'true', Perform strict validation of the input schema (disabled by the
	 * xjc's -nv option).
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.strict")
	private boolean strict = true;

	public boolean getStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * If 'false', the plugin will not write the generated code to disk.
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.writeCode")
	private boolean writeCode = true;

	public boolean getWriteCode() {
		return writeCode;
	}

	public void setWriteCode(boolean writeCode) {
		this.writeCode = writeCode;
	}

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
	@Parameter(defaultValue = "false", property = "maven.xjc2.verbose")
	private boolean verbose;

	public boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * <p>
	 * If 'true', the XJC compiler is set to debug mode (xjc's -debug option).
	 * </p>
	 * <p>
	 * It is automatically set to 'true' when maven is run in debug mode (mvn's
	 * -X option).
	 * </p>
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.debug")
	private boolean debug;

	public boolean getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * <p>
	 * A list of extra XJC's command-line arguments (items must include the dash
	 * '-'). Use this argument to enable the JAXB2 plugins you want to use.
	 * </p>
	 * <p>
	 * Arguments set here take precedence over other mojo parameters.
	 * </p>
	 */
	@Parameter
	private List<String> args = new LinkedList<String>();

	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args.addAll(args);
	}

	/**
	 * If true, no up-to-date check is performed and the XJC always re-generates
	 * the sources. Otherwise schemas will only be recompiled if anything has
	 * changed.
	 * 
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.forceRegenerate")
	private boolean forceRegenerate;

	public boolean getForceRegenerate() {
		return forceRegenerate;
	}

	public void setForceRegenerate(boolean forceRegenerate) {
		this.forceRegenerate = forceRegenerate;
	}

	/**
	 * <p>
	 * If 'true', the generateDirectory will be deleted before the XJC binding
	 * compiler recompiles the source files. Default is false.
	 * </p>
	 * <p>
	 * Note that if set to 'false', the up-to-date check might not work, since
	 * XJC does not regenerate all files (i.e. files for "any" elements under
	 * 'xjc/org/w3/_2001/xmlschema' directory).
	 * </p>
	 * 
	 */
	@Parameter(defaultValue = "false", property = "maven.xjc2.removeOldOutput")
	private boolean removeOldOutput;

	public boolean getRemoveOldOutput() {
		return removeOldOutput;
	}

	public void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	/**
	 * <p>
	 * If 'true', package directories will be cleaned before the XJC binding
	 * compiler generates the source files.
	 * </p>
	 * 
	 */
	@Parameter(defaultValue = "true", property = "maven.xjc2.removeOldPackages")
	private boolean cleanPackageDirectories = true;

	public boolean getCleanPackageDirectories() {
		return cleanPackageDirectories;
	}

	public void setCleanPackageDirectories(boolean removeOldPackages) {
		this.cleanPackageDirectories = removeOldPackages;
	}

	/**
	 * Specifies patters of files produced by this plugin. This is used to check
	 * if produced files are up-to-date. Default value is ** /*.*, ** /*.java,
	 * ** /bgm.ser, ** /jaxb.properties.
	 */
	@Parameter
	private String[] produces = new String[] { "**/*.*", "**/*.java",
			"**/bgm.ser", "**/jaxb.properties" };

	public String[] getProduces() {
		return produces;
	}

	public void setProduces(String[] produces) {
		this.produces = produces;
	}

	/**
	 * A list of of input files or URLs to consider during the up-to-date. By
	 * default it always considers: 1. schema files, 2. binding files, 3.
	 * catalog file, and 4. the pom.xml file of the project executing this
	 * plugin.
	 */
	@Parameter
	private File[] otherDepends;

	public File[] getOtherDepends() {
		return otherDepends;
	}

	public void setOtherDepends(File[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	/**
	 * Target location of the episode file. By default it is
	 * target/generated-sources/xjc/META-INF/sun-jaxb.episode so that the
	 * episode file will appear as META-INF/sun-jaxb.episode in the JAR - just
	 * as XJC wants it.
	 */
	@Parameter(property = "maven.xjc2.episodeFile")
	private File episodeFile;

	public File getEpisodeFile() {
		return episodeFile;
	}

	public void setEpisodeFile(File episodeFile) {
		this.episodeFile = episodeFile;
	}

	/**
	 * If true, the episode file (describing mapping of elements and types to
	 * classes for the compiled schema) will be generated.
	 */
	@Parameter(property = "maven.xjc2.episode", defaultValue = "true")
	private boolean episode = true;

	public boolean getEpisode() {
		return episode;
	}

	public void setEpisode(boolean episode) {
		this.episode = episode;
	}

	/**
	 * If true, marks generated classes using a @Generated annotation - i.e.
	 * turns on XJC -mark-generated option. Default is false.
	 */
	@Parameter(property = "maven.xjc2.markGenerated", defaultValue = "false")
	private boolean markGenerated = false;

	public boolean getMarkGenerated() {
		return markGenerated;
	}

	public void setMarkGenerated(boolean markGenerated) {
		this.markGenerated = markGenerated;
	}

	/**
	 * Project classpath. Used internally when runing the XJC compiler.
	 */
	@Parameter(property = "project.compileClasspathElements", required = true, readonly = true)
	private List classpathElements;

	public List getClasspathElements() {
		return classpathElements;
	}

	public void setClasspathElements(List classpathElements) {
		this.classpathElements = classpathElements;
	}

	/**
	 * XJC plugins to be made available to XJC. They still need to be activated
	 * by using &lt;args/> and enable plugin activation option.
	 */
	@Parameter
	protected Dependency[] plugins;

	public Dependency[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Dependency[] plugins) {
		this.plugins = plugins;
	}

	@Component
	private MavenProject project;

	@Component
	private ArtifactResolver artifactResolver;

	@Component
	private ArtifactMetadataSource artifactMetadataSource;

	@Component
	private ArtifactFactory artifactFactory;

	/**
	 * Location of the local repository.
	 */
	@Parameter(defaultValue = "${localRepository}", required = true)
	private ArtifactRepository localRepository;

	/**
	 * Artifact factory, needed to download source jars.
	 */
	@Component(role = org.apache.maven.project.MavenProjectBuilder.class)
	private MavenProjectBuilder mavenProjectBuilder;

	@Component
	private BuildContext buildContext;

	/**
	 * Plugin artifacts.
	 */
	@Parameter(defaultValue = "${plugin.artifacts}", required = true)
	private List<org.apache.maven.artifact.Artifact> pluginArtifacts;

	/**
	 * If you want to use existing artifacts as episodes for separate
	 * compilation, configure them as episodes/episode elements. It is assumed
	 * that episode artifacts contain an appropriate META-INF/sun-jaxb.episode
	 * resource.
	 */
	@Parameter
	private Dependency[] episodes;

	public Dependency[] getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Dependency[] episodes) {
		this.episodes = episodes;
	}

	/**
	 * Use all of the compile-scope project dependencies as episode artifacts.
	 * It is assumed that episode artifacts contain an appropriate
	 * META-INF/sun-jaxb.episode resource. Default is false.
	 */
	@Parameter
	private boolean useDependenciesAsEpisodes = false;

	public boolean getUseDependenciesAsEpisodes() {
		return useDependenciesAsEpisodes;
	}

	public void setUseDependenciesAsEpisodes(boolean useDependenciesAsEpisodes) {
		this.useDependenciesAsEpisodes = useDependenciesAsEpisodes;
	}

	/**
	 * Scan all compile-scoped project dependencies for XML binding files.
	 */
	@Parameter(defaultValue = "false")
	private boolean scanDependenciesForBindings = false;

	public boolean getScanDependenciesForBindings() {
		return scanDependenciesForBindings;
	}

	public void setScanDependenciesForBindings(
			boolean scanDependenciesForBindings) {
		this.scanDependenciesForBindings = scanDependenciesForBindings;
	}

	/**
	 * Version of the JAXB specification (ex. 2.0, 2.1 or 2.2).
	 */
	@Parameter(defaultValue = "2.2")
	private String specVersion = "2.2";

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	protected void logConfiguration() throws MojoExecutionException {

		logApiConfiguration();

		getLog().info("pluginArtifacts:" + getPluginArtifacts());
		getLog().info("specVersion:" + getSpecVersion());
		getLog().info("encoding:" + getEncoding());
		getLog().info("schemaLanguage:" + getSchemaLanguage());
		getLog().info("schemaDirectory:" + getSchemaDirectory());
		getLog().info("schemaIncludes:" + Arrays.toString(getSchemaIncludes()));
		getLog().info("schemaExcludes:" + Arrays.toString(getSchemaExcludes()));
		getLog().info("schemas:" + Arrays.toString(getSchemas()));
		getLog().info("bindingDirectory:" + getBindingDirectory());
		getLog().info(
				"bindingIncludes:" + Arrays.toString(getBindingIncludes()));
		getLog().info(
				"bindingExcludes:" + Arrays.toString(getBindingExcludes()));
		getLog().info("bindings:" + Arrays.toString(getBindings()));
		getLog().info("disableDefaultExcludes:" + getDisableDefaultExcludes());
		getLog().info("catalog:" + getCatalog());
		getLog().info("catalogResolver:" + getCatalogResolver());
		getLog().info("generatePackage:" + getGeneratePackage());
		getLog().info("generateDirectory:" + getGenerateDirectory());
		getLog().info("readOnly:" + getReadOnly());
		getLog().info("extension:" + getExtension());
		getLog().info("strict:" + getStrict());
		getLog().info("writeCode:" + getWriteCode());
		getLog().info("verbose:" + getVerbose());
		getLog().info("debug:" + getDebug());
		getLog().info("args:" + getArgs());
		getLog().info("forceRegenerate:" + getForceRegenerate());
		getLog().info("removeOldOutput:" + getRemoveOldOutput());
		getLog().info("produces:" + Arrays.toString(getProduces()));
		getLog().info("otherDepends:" + getOtherDepends());
		getLog().info("episodeFile:" + getEpisodeFile());
		getLog().info("episode:" + getEpisode());
		getLog().info("classpathElements:" + getClasspathElements());
		getLog().info("plugins:" + Arrays.toString(getPlugins()));
		getLog().info("episodes:" + Arrays.toString(getEpisodes()));
		getLog().info(
				"useDependenciesAsEpisodes:" + getUseDependenciesAsEpisodes());
		getLog().info(
				"scanDependenciesForBindings:"
						+ getScanDependenciesForBindings());
		getLog().info("xjcPlugins:" + Arrays.toString(getPlugins()));
		getLog().info("episodes:" + Arrays.toString(getEpisodes()));
	}

	private static final String XML_SCHEMA_CLASS_NAME = "XmlSchema";

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	private static final String XML_SCHEMA_CLASS_QNAME = "javax.xml.bind.annotation."
			+ XML_SCHEMA_CLASS_NAME;

	public ArtifactResolver getArtifactResolver() {
		return artifactResolver;
	}

	public void setArtifactResolver(ArtifactResolver artifactResolver) {
		this.artifactResolver = artifactResolver;
	}

	private static final String XML_SCHEMA_RESOURCE_NAME = XML_SCHEMA_CLASS_NAME
			+ ".class";

	public ArtifactMetadataSource getArtifactMetadataSource() {
		return artifactMetadataSource;
	}

	public void setArtifactMetadataSource(
			ArtifactMetadataSource artifactMetadataSource) {
		this.artifactMetadataSource = artifactMetadataSource;
	}

	private static final String XML_SCHEMA_RESOURCE_QNAME = "/javax/xml/bind/annotation/"
			+ XML_SCHEMA_RESOURCE_NAME;

	public ArtifactFactory getArtifactFactory() {
		return artifactFactory;
	}

	public void setArtifactFactory(ArtifactFactory artifactFactory) {
		this.artifactFactory = artifactFactory;
	}

	private static final String XML_ELEMENT_REF_CLASS_NAME = "XmlElementRef";

	public ArtifactRepository getLocalRepository() {
		return localRepository;
	}

	public void setLocalRepository(ArtifactRepository localRepository) {
		this.localRepository = localRepository;
	}

	private static final String XML_ELEMENT_REF_CLASS_QNAME = "javax.xml.bind.annotation."
			+ XML_ELEMENT_REF_CLASS_NAME;

	public MavenProjectBuilder getMavenProjectBuilder() {
		return mavenProjectBuilder;
	}

	public void setMavenProjectBuilder(MavenProjectBuilder mavenProjectBuilder) {
		this.mavenProjectBuilder = mavenProjectBuilder;
	}

	public BuildContext getBuildContext() {
		return buildContext;
	}

	public void setBuildContext(BuildContext buildContext) {
		this.buildContext = buildContext;
	}

	protected void logApiConfiguration() {

		try {
			final Class<?> xmlSchemaClass = Class
					.forName(XML_SCHEMA_CLASS_QNAME);

			final URL resource = xmlSchemaClass
					.getResource(XML_SCHEMA_RESOURCE_NAME);

			final String draftLocation = resource.toExternalForm();
			final String location;
			if (draftLocation.endsWith(XML_SCHEMA_RESOURCE_QNAME)) {
				location = draftLocation.substring(0, draftLocation.length()
						- XML_SCHEMA_RESOURCE_QNAME.length());
			} else {
				location = draftLocation;
			}
			getLog().info("JAXB API is loaded from the [" + location + "].");

			try {
				xmlSchemaClass.getMethod("location");

				final Class<?> xmlElementRefClass = Class
						.forName(XML_ELEMENT_REF_CLASS_QNAME);

				try {
					xmlElementRefClass.getMethod("required");
					getLog().info("Detected JAXB API version [2.2].");
				} catch (NoSuchMethodException nsmex2) {
					getLog().info("Detected JAXB API version [2.1].");
				}
			} catch (NoSuchMethodException nsmex1) {
				getLog().info("Detected JAXB API version [2.0].");

			}
		} catch (ClassNotFoundException cnfex) {
			getLog().error(
					"Could not find JAXB 2.x API classes. Make sure JAXB 2.x API is on the classpath.");
		}
	}

	public List<org.apache.maven.artifact.Artifact> getPluginArtifacts() {
		return pluginArtifacts;
	}

	public void setPluginArtifacts(
			List<org.apache.maven.artifact.Artifact> plugingArtifacts) {
		this.pluginArtifacts = plugingArtifacts;
	}

	protected List<URL> createResourceEntryUrls(ResourceEntry resourceEntry,
			String defaultDirectory, String[] defaultIncludes,
			String[] defaultExcludes) throws MojoExecutionException {
		if (resourceEntry == null) {
			return Collections.emptyList();
		} else {
			final List<URL> urls = new LinkedList<URL>();
			if (resourceEntry.getFileset() != null) {
				final FileSet fileset = resourceEntry.getFileset();
				urls.addAll(createFileSetUrls(fileset, defaultDirectory,
						defaultIncludes, defaultExcludes));
			}
			if (resourceEntry.getUrl() != null) {
				String urlDraft = resourceEntry.getUrl();
				urls.add(createUrl(urlDraft));
			}
			if (resourceEntry.getDependencyResource() != null) {
				urls.add(resolveDependencyResource(resourceEntry
						.getDependencyResource()));
			}
			return urls;
		}
	}

	public URL resolveDependencyResource(DependencyResource dependencyResource)
			throws MojoExecutionException {

		if (dependencyResource.getGroupId() == null) {
			throw new MojoExecutionException(MessageFormat.format(
					"Dependency resource [{0}] does define the groupId.",
					dependencyResource));
		}

		if (dependencyResource.getArtifactId() == null) {
			throw new MojoExecutionException(
					MessageFormat
							.format("Dependency resource [{0}] does not define the artifactId.",
									dependencyResource));
		}

		if (dependencyResource.getType() == null) {
			throw new MojoExecutionException(MessageFormat.format(
					"Dependency resource [{0}] does not define the type.",
					dependencyResource));
		}

		if (getProject().getDependencyManagement() != null) {
			merge(dependencyResource, getProject().getDependencyManagement()
					.getDependencies());
		}
		if (getProject().getDependencies() != null) {
			merge(dependencyResource, getProject().getDependencies());
		}

		if (dependencyResource.getVersion() == null) {
			throw new MojoExecutionException(MessageFormat.format(
					"Dependency resource [{0}] does not define the version.",
					dependencyResource));
		}

		try {
			@SuppressWarnings("unchecked")
			final Set<Artifact> artifacts = MavenMetadataSource
					.createArtifacts(getArtifactFactory(),
							Arrays.asList(dependencyResource),
							Artifact.SCOPE_RUNTIME, null, getProject());

			if (artifacts.size() != 1) {
				getLog().error(
						MessageFormat
								.format("Resolved dependency resource [{0}] to artifacts [{1}].",
										dependencyResource, artifacts));
				throw new MojoExecutionException(MessageFormat.format(
						"Could not create artifact for dependency [{0}].",
						dependencyResource));
			}

			final Artifact artifact = artifacts.iterator().next();

			getArtifactResolver().resolve(artifact,
					getProject().getRemoteArtifactRepositories(),
					getLocalRepository());

			final String resource = dependencyResource.getResource();
			if (resource == null) {
				throw new MojoExecutionException(
						MessageFormat
								.format("Dependency resource [{0}] does not define the resource.",
										dependencyResource));
			}
			final URL resourceURL = createArtifactResourceUrl(artifact,
					resource);
			getLog().debug(
					MessageFormat
							.format("Resolved dependency resource [{0}] to resource URL [{1}].",
									dependencyResource, resourceURL));
			return resourceURL;
		} catch (ArtifactNotFoundException anfex) {
			throw new MojoExecutionException(MessageFormat.format(
					"Could not find artifact for dependency [{0}].",
					dependencyResource));

		} catch (InvalidDependencyVersionException e) {
			throw new MojoExecutionException(MessageFormat.format(
					"Invalid version of dependency [{0}].", dependencyResource));
		} catch (ArtifactResolutionException e) {
			throw new MojoExecutionException(MessageFormat.format(
					"Could not resolver artifact for dependency [{0}].",
					dependencyResource));
		}
	}

	private URL createArtifactResourceUrl(final Artifact artifact,
			String resource) throws MojoExecutionException {
		final File artifactFile = artifact.getFile();

		if (artifactFile.isDirectory()) {
			final File resourceFile = new File(artifactFile, resource);
			try {
				return resourceFile.toURI().toURL();
			} catch (MalformedURLException murlex) {
				throw new MojoExecutionException(
						MessageFormat
								.format("Could not create an URL for dependency directory [{0}] and resource [{1}].",
										artifactFile, resource));
			}
		} else {
			try {
				return new URL("jar:"
						+ artifactFile.toURI().toURL().toExternalForm() + "!/"
						+ resource);
			} catch (MalformedURLException murlex) {
				throw new MojoExecutionException(
						MessageFormat
								.format("Could not create an URL for dependency file [{0}] and resource [{1}].",
										artifactFile, resource));

			}
		}
	}

	private URL createUrl(String urlDraft) throws MojoExecutionException {
		try {
			final URL url = new URL(urlDraft);
			return url;
		} catch (MalformedURLException murlex) {
			throw new MojoExecutionException(MessageFormat.format(
					"Could not create an URL from string [{0}].", urlDraft),
					murlex);
		}
	}

	private List<URL> createFileSetUrls(final FileSet fileset,
			String defaultDirectory, String[] defaultIncludes,
			String defaultExcludes[]) throws MojoExecutionException {
		final String draftDirectory = fileset.getDirectory();
		final String directory = draftDirectory == null ? defaultDirectory
				: draftDirectory;
		final List<String> includes;
		final List<String> draftIncludes = fileset.getIncludes();
		if (draftIncludes == null || draftIncludes.isEmpty()) {
			includes = defaultIncludes == null ? Collections
					.<String> emptyList() : Arrays.asList(defaultIncludes);
		} else {
			includes = draftIncludes;
		}

		final List<String> excludes;
		final List<String> draftExcludes = fileset.getExcludes();
		if (draftExcludes == null || draftExcludes.isEmpty()) {
			excludes = defaultExcludes == null ? Collections
					.<String> emptyList() : Arrays.asList(defaultExcludes);
		} else {
			excludes = draftExcludes;
		}
		String[] includesArray = includes.toArray(new String[includes.size()]);
		String[] excludesArray = excludes.toArray(new String[excludes.size()]);
		try {
			final List<File> files = IOUtils.scanDirectoryForFiles(
					getBuildContext(), new File(directory), includesArray,
					excludesArray, !getDisableDefaultExcludes());

			final List<URL> urls = new ArrayList<URL>(files.size());

			for (final File file : files) {
				try {
					final URL url = file.toURI().toURL();
					urls.add(url);
				} catch (MalformedURLException murlex) {
					throw new MojoExecutionException(
							MessageFormat.format(
									"Could not create an URL for the file [{0}].",
									file), murlex);
				}
			}
			return urls;
		} catch (IOException ioex) {
			throw new MojoExecutionException(
					MessageFormat
							.format("Could not scan directory [{0}] for files with inclusion [{1}]  and exclusion [{2}].",
									directory, includes, excludes));
		}
	}

	private void merge(Dependency dependency,
			final List<Dependency> managedDependencies) {

		for (Dependency managedDependency : managedDependencies) {
			if (dependency.getManagementKey().equals(
					managedDependency.getManagementKey())) {
				ArtifactUtils.mergeDependencyWithDefaults(dependency,
						managedDependency);
			}
		}
	}

	protected abstract OptionsFactory<O> getOptionsFactory();

	protected void cleanPackageDirectory(final File packageDirectory) {
		final File[] files = packageDirectory.listFiles(new FileFilter() {

			// @Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}

}
