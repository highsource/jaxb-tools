package org.jvnet.jaxb2.maven2;

import java.io.File;
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
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.apache.maven.project.artifact.MavenMetadataSource;
import org.jfrog.maven.annomojo.annotations.MojoComponent;
import org.jfrog.maven.annomojo.annotations.MojoParameter;
import org.jvnet.jaxb2.maven2.util.ArtifactUtils;
import org.jvnet.jaxb2.maven2.util.IOUtils;

import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

public abstract class AbstractXJC2Mojo<O> extends AbstractMojo implements
		DependencyResourceResolver {

	private String schemaLanguage;

	/**
	 * Type of input schema language. One of: DTD, XMLSCHEMA, RELAXNG,
	 * RELAXNG_COMPACT, WSDL, AUTODETECT. If unspecified, it is assumed
	 * AUTODETECT.
	 */
	@MojoParameter(expression = "${maven.xjc2.schemaLanguage}", description = "Type of input schema language. One of: DTD, XMLSCHEMA, RELAXNG, RELAXNG_COMPACT, WSDL, AUTODETECT. If unspecified, it is assumed AUTODETECT.")
	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public void setSchemaLanguage(String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	private File schemaDirectory;

	/**
	 * The source directory containing *.xsd schema files. Notice that binding
	 * files are searched by default in this directory.
	 * 
	 */
	@MojoParameter(defaultValue = "src/main/resources", expression = "${maven.xjc2.schemaDirectory}", required = true, description = "Specifies the schema directory.")
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
	@MojoParameter(description = "Specifies file patterns to include as schemas. Default value is *.xsd.")
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
	@MojoParameter(description = "Specifies file patterns of schemas to exclude. By default, nothing is excluded.")
	public String[] getSchemaExcludes() {
		return schemaExcludes;
	}

	public void setSchemaExcludes(String[] schemaExcludes) {
		this.schemaExcludes = schemaExcludes;
	}

	private ResourceEntry[] schemas = new ResourceEntry[0];

	/**
	 * A list of schema resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@MojoParameter(description = "Specifies schema resources.")
	public ResourceEntry[] getSchemas() {
		return schemas;
	}

	public void setSchemas(ResourceEntry[] schemas) {
		this.schemas = schemas;
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
	@MojoParameter(expression = "${maven.xjc2.bindingDirectory}", description = "Specifies the binding directory, defaults to the schema directory.")
	public File getBindingDirectory() {
		return bindingDirectory != null ? bindingDirectory
				: getSchemaDirectory();
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
	@MojoParameter(description = "Specifies file patterns to include as bindings. Default value is *.xjb.")
	public String[] getBindingIncludes() {
		return bindingIncludes;
	}

	public void setBindingIncludes(String[] bindingIncludes) {
		this.bindingIncludes = bindingIncludes;
	}

	private String[] bindingExcludes;

	/**
	 * A list of regular expression file search patterns to specify the binding
	 * files to be excluded from the <code>bindingIncludes</code>. Searching is
	 * based from the root of bindingDirectory.
	 */
	@MojoParameter(description = "Specifies file patterns of bindings to exclude. By default, nothing is excluded.")
	public String[] getBindingExcludes() {
		return bindingExcludes;
	}

	public void setBindingExcludes(String[] bindingExcludes) {
		this.bindingExcludes = bindingExcludes;
	}

	private ResourceEntry[] bindings = new ResourceEntry[0];

	/**
	 * A list of binding resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@MojoParameter(description = "Specifies binding resources.")
	public ResourceEntry[] getBindings() {
		return bindings;
	}

	public void setBindings(ResourceEntry[] bindings) {
		this.bindings = bindings;
	}

	private boolean disableDefaultExcludes;

	/**
	 * If 'true', maven's default exludes are NOT added to all the excludes
	 * lists.
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.disableDefaultExcludes}", description = "If set to true, Maven default exludes are NOT added to all the excludes lists.")
	public boolean getDisableDefaultExcludes() {
		return disableDefaultExcludes;
	}

	public void setDisableDefaultExcludes(boolean disableDefaultExcludes) {
		this.disableDefaultExcludes = disableDefaultExcludes;
	}

	private File catalog;

	/**
	 * Specify the catalog file to resolve external entity references (xjc's
	 * -catalog option) </p>
	 * <p>
	 * Support TR9401, XCatalog, and OASIS XML Catalog format. See the
	 * catalog-resolver sample and this article for details.
	 * </p>
	 */
	@MojoParameter(expression = "${maven.xjc2.catalog}", description = "Specify the catalog file to resolve external entity references (XJC -catalog option).")
	public File getCatalog() {
		return catalog;
	}

	public void setCatalog(File catalog) {
		this.catalog = catalog;
	}

	private ResourceEntry[] catalogs = new ResourceEntry[0];

	/**
	 * A list of catalog resources which could includes file sets, URLs, Maven
	 * artifact resources.
	 */
	@MojoParameter(description = "Specifies catalogs as filesets, URLs or Maven artifact resources.")
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

	protected String catalogResolver = null;

	/**
	 * Provides the class name of the catalog resolver.
	 * 
	 * @return Class name of the catalog resolver.
	 */
	@MojoParameter(expression = "${maven.xjc2.catalogResolver}", description = "Class name of the catalog resolver.")
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
	@MojoParameter(expression = "${maven.xjc2.generatePackage}", description = "The generated classes will all be placed under this Java package, unless otherwise specified in the schemas. If left unspecified, the package will be derived from the schemas only.")
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
	 * For instance, if you specify <code>generateDirectory="doe/ray"</code> and
	 * <code>generatePackage="org.here"</code>, then files are generated to
	 * <code>doe/ray/org/here</code>.
	 * </p>
	 */
	@MojoParameter(defaultValue = "${project.build.directory}/generated-sources/xjc", expression = "${maven.xjc2.generateDirectory}", required = true, description = "Target directory for the generated code.")
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

	private boolean addCompileSourceRoot = true;

	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.addCompileSourceRoot}", required = false, description = "If set to true (default), adds target directory as a compile source root of this Maven project.")
	public boolean getAddCompileSourceRoot() {
		return addCompileSourceRoot;
	}

	public void setAddCompileSourceRoot(boolean addCompileSourceRoot) {
		this.addCompileSourceRoot = addCompileSourceRoot;
	}

	private boolean addTestCompileSourceRoot = false;

	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.addTestCompileSourceRoot}", required = false, description = "If set to true, adds target directory as a test compile source root of this Maven project. Default value is false.")
	public boolean getAddTestCompileSourceRoot() {
		return addTestCompileSourceRoot;
	}

	public void setAddTestCompileSourceRoot(boolean addTestCompileSourceRoot) {
		this.addTestCompileSourceRoot = addTestCompileSourceRoot;
	}

	private boolean readOnly;

	/**
	 * If 'true', the generated Java source files are set as read-only (xjc's
	 * -readOnly option).
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.readOnly}", description = "If true, the generated Java source files are set as read-only (XJC's -readOnly option).")
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
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.extension}", description = " If true, the XJC binding compiler will run in the extension mode (XJC's -extension option). Otherwise, it will run in the strict conformance mode. Please note that you must enable the extension mode if you use vendor extensions in your bindings.")
	public boolean getExtension() {
		return extension;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	private boolean strict = true;

	/**
	 * If 'true', Perform strict validation of the input schema (xjc's -nv
	 * option).
	 */
	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.strict}", description = "If true, XJC will perform strict validation of the input schema. If strict is set to false XJC will be run with -nv, this disables strict validation of schemas.")
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
	@MojoParameter(defaultValue = "true", expression = "${maven.xjc2.writeCode}", description = "If set to false, the plugin will not write the generated code to disk.")
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
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.verbose}", description = "If true, the plugin and the XJC compiler are both set to verbose mode (XJC's -verbose option). It is automatically set to true when Maven is run in debug mode (mvn's -X option).")
	public boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private boolean debug;

	/**
	 * <p>
	 * If 'true', the XJC compiler is set to debug mode (xjc's -debug option).
	 * </p>
	 * <p>
	 * It is automatically set to 'true' when maven is run in debug mode (mvn's
	 * -X option).
	 * </p>
	 */
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.debug}", description = "If true, the XJC compiler is set to debug mode (XJC's -debug option).")
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
	@MojoParameter(description = "A list of extra XJC's command-line arguments (items must include the dash \"-\"). Use this argument to enable the JAXB2 plugins you want to use.")
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
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.forceRegenerate}", description = "If true, no up-to-date check is performed and the XJC always re-generates the sources. Otherwise schemas will only be recompiled if anything has changed.")
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
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.removeOldOutput}", description = "If true, the generateDirectory will be deleted before the XJC binding compiler recompiles the source files. Default is false.")
	public boolean getRemoveOldOutput() {
		return removeOldOutput;
	}

	public void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	private String[] produces = new String[] { "**/*.*", "**/*.java",
			"**/bgm.ser", "**/jaxb.properties" };

	@MojoParameter(description = "Specifies patters of files produced by this plugin. This is used to check if produced files are up-to-date. Default value is **/*.*, **/*.java, **/bgm.ser, **/jaxb.properties.")
	public String[] getProduces() {
		return produces;
	}

	public void setProduces(String[] produces) {
		this.produces = produces;
	}

	private File[] otherDepends;

	/**
	 * A list of of input files or URLs to consider during the up-to-date. By
	 * default it always considers: 1. schema files, 2. binding files, 3.
	 * catalog file, and 4. the pom.xml file of the project executing this
	 * plugin.
	 */
	@MojoParameter(description = "A list of of input files or URLs to consider during the up-to-date. By  default it always considers: 1. schema files, 2. binding files, 3. catalog file, and 4. the pom.xml file of the project executing this plugin.")
	public File[] getOtherDepends() {
		return otherDepends;
	}

	public void setOtherDepends(File[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	private File episodeFile;

	@MojoParameter(expression = "${maven.xjc2.episodeFile}", description = "Target location of the episode file. By default it is target/generated-sources/xjc/META-INF/sun-jaxb.episode so that the episode file will appear as META-INF/sun-jaxb.episode in the JAR - just as XJC wants it.")
	public File getEpisodeFile() {
		return episodeFile;
	}

	public void setEpisodeFile(File episodeFile) {
		this.episodeFile = episodeFile;
	}

	private boolean episode = true;

	@MojoParameter(expression = "${maven.xjc2.episode}", defaultValue = "true", description = "If true, the episode file (describing mapping of elements and types to classes for the compiled schema) will be generated.")
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
	@MojoParameter(expression = "${project.compileClasspathElements}", required = true, readonly = true, description = "Internal use.")
	public List getClasspathElements() {
		return classpathElements;
	}

	public void setClasspathElements(List classpathElements) {
		this.classpathElements = classpathElements;
	}

	protected Dependency[] plugins;

	/**
	 * XJC plugins to be made available to XJC. They still need to be activated
	 * by using &lt;args> and enable plugin activation option.
	 */
	@MojoParameter(description = "Configures artifacts of the custom JAXB2 plugins you want to use.")
	public Dependency[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Dependency[] plugins) {
		this.plugins = plugins;
	}

	private MavenProject project;

	private ArtifactResolver artifactResolver;

	private ArtifactMetadataSource artifactMetadataSource;

	private ArtifactFactory artifactFactory;

	private ArtifactRepository localRepository;

	private MavenProjectBuilder mavenProjectBuilder;

	private List<org.apache.maven.artifact.Artifact> pluginArtifacts;

	private Dependency[] episodes;

	@MojoParameter(description = "If you want to use existing artifacts as episodes for separate compilation, configure them as episodes/episode elements. It is assumed that episode artifacts contain an appropriate META-INF/sun-jaxb.episode resource.")
	public Dependency[] getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Dependency[] episodes) {
		this.episodes = episodes;
	}

	private boolean useDependenciesAsEpisodes = false;

	@MojoParameter(description = "Use all of the compile-scope project dependencies as episode artifacts. "
			+ "It is assumed that episode artifacts contain an appropriate META-INF/sun-jaxb.episode resource. "
			+ "Default is false.")
	public boolean getUseDependenciesAsEpisodes() {
		return useDependenciesAsEpisodes;
	}

	public void setUseDependenciesAsEpisodes(boolean useDependenciesAsEpisodes) {
		this.useDependenciesAsEpisodes = useDependenciesAsEpisodes;
	}

	private String specVersion = "2.1";

	@MojoParameter(defaultValue = "2.1", description = "Version of the JAXB specification (ex. 2.0, 2.1 or 2.2).")
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
		getLog().info("xjcPlugins:" + Arrays.toString(getPlugins()));
		getLog().info("episodes:" + Arrays.toString(getEpisodes()));
	}

	private static final String XML_SCHEMA_CLASS_NAME = "XmlSchema";

	@MojoParameter(expression = "${project}", required = true, readonly = true, description = "Internal use.")
	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	private static final String XML_SCHEMA_CLASS_QNAME = "javax.xml.bind.annotation."
			+ XML_SCHEMA_CLASS_NAME;

	@MojoComponent
	public ArtifactResolver getArtifactResolver() {
		return artifactResolver;
	}

	public void setArtifactResolver(ArtifactResolver artifactResolver) {
		this.artifactResolver = artifactResolver;
	}

	private static final String XML_SCHEMA_RESOURCE_NAME = XML_SCHEMA_CLASS_NAME
			+ ".class";

	@MojoComponent
	public ArtifactMetadataSource getArtifactMetadataSource() {
		return artifactMetadataSource;
	}

	public void setArtifactMetadataSource(
			ArtifactMetadataSource artifactMetadataSource) {
		this.artifactMetadataSource = artifactMetadataSource;
	}

	private static final String XML_SCHEMA_RESOURCE_QNAME = "/javax/xml/bind/annotation/"
			+ XML_SCHEMA_RESOURCE_NAME;

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

	private static final String XML_ELEMENT_REF_CLASS_NAME = "XmlElementRef";

	@MojoParameter(expression = "${localRepository}", required = true, description = "Internal use.")
	public ArtifactRepository getLocalRepository() {
		return localRepository;
	}

	public void setLocalRepository(ArtifactRepository localRepository) {
		this.localRepository = localRepository;
	}

	private static final String XML_ELEMENT_REF_CLASS_QNAME = "javax.xml.bind.annotation."
			+ XML_ELEMENT_REF_CLASS_NAME;

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

	@MojoParameter(expression = "${plugin.artifacts}", required = true, description = "Internal use.")
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
							Arrays.asList(dependencyResource), "runtime", null,
							getProject());

			if (artifacts.size() != 1) {
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
			return createArtifactResourceUrl(artifact, resource);
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
			final List<File> files = IOUtils.scanDirectoryForFiles(new File(
					directory), includesArray, excludesArray,
					!getDisableDefaultExcludes());

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

}
