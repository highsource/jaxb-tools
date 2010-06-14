package org.jvnet.jaxb2.maven2;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.jfrog.maven.annomojo.annotations.MojoComponent;
import org.jfrog.maven.annomojo.annotations.MojoParameter;

import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

public abstract class AbstractXJC2Mojo extends AbstractMojo {

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
	 * files to be excluded from the <code>bindingIncludes</code>. Searching is
	 * based from the root of bindingDirectory.
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
	 * -catalog option) </p>
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

	protected String catalogResolver = CatalogResolver.class.getName();

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
	 * For instance, if you specify <code>generateDirectory="doe/ray"</code> and
	 * <code>generatePackage="org.here"</code>, then files are generated to
	 * <code>doe/ray/org/here</code>.
	 * </p>
	 */
	@MojoParameter(defaultValue = "${project.build.directory}/generated-sources/xjc", expression = "${maven.xjc2.generateDirectory}", required = true)
	public File getGenerateDirectory() {
		return generateDirectory;
	}

	public void setGenerateDirectory(File generateDirectory) {
		this.generateDirectory = generateDirectory;

		if (getEpisodeFile() == null) {
			final File episodeFile = new File(getGenerateDirectory(), "META-INF"
					+ File.separator + "sun-jaxb.episode");
			setEpisodeFile(episodeFile);
		}
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
	 * If 'true', the XJC compiler is set to debug mode (xjc's -debug option).
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
	@MojoParameter(defaultValue = "false", expression = "${maven.xjc2.removeOldOutput}")
	public boolean getRemoveOldOutput() {
		return removeOldOutput;
	}

	public void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	private File[] otherDepends;

	/**
	 * A list of of input files or URLs to consider during the up-to-date. By
	 * default it always considers: 1. schema files, 2. binding files, 3.
	 * catalog file, and 4. the pom.xml file of the project executing this
	 * plugin.
	 */
	@MojoParameter
	public File[] getOtherDepends() {
		return otherDepends;
	}

	public void setOtherDepends(File[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	private File episodeFile;

	@MojoParameter(expression = "${maven.xjc2.episodeFile}")
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

	protected Dependency[] plugins;

	/**
	 * XJC plugins to be made available to XJC. They still need to be activated
	 * by using &lt;args> and enable plugin activation option.
	 */
	@MojoParameter
	public Dependency[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Dependency[] plugins) {
		this.plugins = plugins;
	}

	private Dependency[] episodes;

	private MavenProject project;

	private ArtifactResolver artifactResolver;

	private ArtifactMetadataSource artifactMetadataSource;

	private ArtifactFactory artifactFactory;

	private ArtifactRepository localRepository;

	private MavenProjectBuilder mavenProjectBuilder;

	private List<org.apache.maven.artifact.Artifact> pluginArtifacts;

	@MojoParameter
	public Dependency[] getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Dependency[] episodes) {
		this.episodes = episodes;
	}

	private String specVersion = "2.1";

	@MojoParameter(defaultValue = "2.1")
	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	protected void logConfiguration() throws MojoExecutionException {

		logApiConfiguration();

		getLog().info("pluginArtifacts:" + getPluginArtifacts());
		getLog().info("schemaLanguage:" + getSchemaLanguage());
		getLog().info("schemaDirectory:" + getSchemaDirectory());
		getLog().info("schemaIncludes:" + getSchemaIncludes());
		getLog().info("schemaExcludes:" + getSchemaExcludes());
		getLog().info("bindingDirectory:" + getBindingDirectory());
		getLog().info("bindingIncludes:" + getBindingIncludes());
		getLog().info("bindingExcludes:" + getBindingExcludes());
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
		getLog().info("otherDepends:" + getOtherDepends());
		getLog().info("episodeFile:" + getEpisodeFile());
		getLog().info("episode:" + getEpisode());
		getLog().info("classpathElements:" + getClasspathElements());
		getLog().info("plugins:" + getPlugins());
		getLog().info("episodes:" + getEpisodes());
		getLog().info("xjcPlugins:" + getPlugins());
		getLog().info("episodes:" + getEpisodes());
		getLog().info("specVersion:" + getSpecVersion());
	}

	private static final String XML_SCHEMA_CLASS_NAME = "XmlSchema";

	@MojoParameter(expression = "${project}", required = true, readonly = true)
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

	@MojoParameter(expression = "${localRepository}", required = true)
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
			getLog()
					.error(
							"Could not find JAXB 2.x API classes. Make sure JAXB 2.x API is on the classpath.");
		}
	}

	@MojoParameter(expression = "${plugin.artifacts}", required = true)
	public List<org.apache.maven.artifact.Artifact> getPluginArtifacts() {
		return pluginArtifacts;
	}

	public void setPluginArtifacts(
			List<org.apache.maven.artifact.Artifact> plugingArtifacts) {
		this.pluginArtifacts = plugingArtifacts;
	}

}
