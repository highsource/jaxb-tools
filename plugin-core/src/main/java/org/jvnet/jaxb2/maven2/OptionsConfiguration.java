package org.jvnet.jaxb2.maven2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jvnet.jaxb2.maven2.util.IOUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

public class OptionsConfiguration {

	private final String encoding;

	private final String schemaLanguage;
	private final List<URI> schemas;
	private final List<URI> bindings;
	private final List<URI> catalogs;

	private final CatalogResolver catalogResolver;

	private final String generatePackage;

	private final File generateDirectory;

	private final boolean readOnly;

	private final boolean packageLevelAnnotations;
	private final boolean noFileHeader;
	private final boolean enableIntrospection;
	private final boolean disableXmlSecurity;
	private final String accessExternalSchema;
	private final String accessExternalDTD;
	private final boolean contentForWildcard;

	private final boolean extension;

	private final boolean strict;

	private final boolean verbose;

	private final boolean debugMode;

	private final List<String> arguments;

	private final List<URL> plugins;

	private final String specVersion;

	public OptionsConfiguration(String encoding, String schemaLanguage,
			List<URI> schemas, List<URI> bindings, List<URI> catalogs,
			CatalogResolver catalogResolver, String generatePackage,
			File generateDirectory, boolean readOnly,
			boolean packageLevelAnnotations, boolean noFileHeader,
			boolean enableIntrospection, boolean disableXmlSecurity,
			String accessExternalSchema, String accessExternalDTD,
			boolean contentForWildcard,

			boolean extension, boolean strict, boolean verbose,
			boolean debugMode, List<String> arguments, List<URL> plugins,
			String specVersion) {
		super();
		this.encoding = encoding;
		this.schemaLanguage = schemaLanguage;
		this.schemas = schemas;
		this.bindings = bindings;
		this.catalogs = catalogs;
		this.catalogResolver = catalogResolver;
		this.generatePackage = generatePackage;
		this.generateDirectory = generateDirectory;
		this.readOnly = readOnly;
		this.packageLevelAnnotations = packageLevelAnnotations;
		this.noFileHeader = noFileHeader;
		this.enableIntrospection = enableIntrospection;
		this.disableXmlSecurity = disableXmlSecurity;
		this.accessExternalSchema = accessExternalSchema;
		this.accessExternalDTD = accessExternalDTD;
		this.contentForWildcard = contentForWildcard;
		this.extension = extension;
		this.strict = strict;
		this.verbose = verbose;
		this.debugMode = debugMode;
		this.arguments = arguments;
		this.plugins = plugins;
		this.specVersion = specVersion;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public List<URI> getSchemas() {
		return schemas;
	}

	public List<InputSource> getGrammars(EntityResolver resolver)
			throws IOException, SAXException {
		final List<URI> schemas = getSchemas();
		return getInputSources(schemas, resolver);
	}

	public List<URI> getBindings() {
		return bindings;
	}

	public List<InputSource> getBindFiles(EntityResolver resolver)
			throws IOException, SAXException {
		final List<URI> bindings = getBindings();
		return getInputSources(bindings, resolver);
	}

	private List<InputSource> getInputSources(final List<URI> uris,
			EntityResolver resolver) throws IOException, SAXException {
		final List<InputSource> inputSources = new ArrayList<InputSource>(
				uris.size());
		for (final URI uri : uris) {
			InputSource inputSource = IOUtils.getInputSource(uri);
			if (resolver != null) {
				final InputSource resolvedInputSource = resolver.resolveEntity(
						inputSource.getPublicId(), inputSource.getSystemId());
				if (resolvedInputSource != null) {
					inputSource = resolvedInputSource;
				}
			}
			inputSources.add(inputSource);
		}
		return inputSources;
	}
	
	public boolean hasCatalogs()
	{
		return !this.catalogs.isEmpty();
	}

	public List<URI> getCatalogs() {
		final CatalogResolver resolver = getCatalogResolver();
		if (resolver == null) {
			return this.catalogs;
		} else {
			final List<URI> uris = new ArrayList<URI>(this.catalogs.size());
			for (URI uri : catalogs) {
				final String resolvedUri = resolver.getResolvedEntity(null,
						uri.toString());
				if (resolvedUri != null) {
					try {
						uri = new URI(resolvedUri);
					} catch (URISyntaxException ignored) {

					}
				}
				uris.add(uri);
			}
			return uris;
		}
	}

	public CatalogResolver getCatalogResolver() {
		return catalogResolver;
	}

	public String getGeneratePackage() {
		return generatePackage;
	}

	public File getGenerateDirectory() {
		return generateDirectory;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isPackageLevelAnnotations() {
		return packageLevelAnnotations;
	}

	public boolean isNoFileHeader() {
		return noFileHeader;
	}

	public boolean isEnableIntrospection() {
		return enableIntrospection;
	}

	public boolean isDisableXmlSecurity() {
		return disableXmlSecurity;
	}

	public String getAccessExternalSchema() {
		return accessExternalSchema;
	}

	public String getAccessExternalDTD() {
		return accessExternalDTD;
	}

	public boolean isContentForWildcard() {
		return contentForWildcard;
	}

	public boolean isExtension() {
		return extension;
	}

	public boolean isStrict() {
		return strict;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public List<URL> getPlugins() {
		return plugins;
	}

	@Override
	public String toString() {
		return MessageFormat.format("OptionsConfiguration [" +
		//

				"specVersion={0}\n " +
				//
				"generateDirectory={1}\n " +
				//
				"generatePackage={2}\n " +
				//
				"schemaLanguage={3}\n " +
				//
				"schemas={4}\n " +
				//
				"bindings={5}\n " +
				//
				"plugins={6}\n " +
				//
				"catalogs={7}\n " +
				//
				"catalogResolver={8}\n " +
				//
				"readOnly={9}\n " +
				//
				"packageLevelAnnotations={10}\n " +
				//
				"noFileHeader={11}\n " +
				//
				"enableIntrospection={12}\n " +
				//
				"disableXmlSecurity={13}\n " +
				//
				"accessExternalSchema={14}\n " +
				//
				"accessExternalDTD={15}\n " +

				//
				"contentForWildcard={16}\n " +
				//
				"extension={17}\n " +
				//
				"strict={18}\n " +
				//
				"verbose={19}\n " +
				//
				"debugMode={20}\n " +
				//
				"arguments={19}" +
				//
				"]", specVersion, generateDirectory, generatePackage,
				schemaLanguage, schemas, bindings, plugins, catalogs,
				catalogResolver, readOnly, packageLevelAnnotations,
				noFileHeader, enableIntrospection, disableXmlSecurity,
				accessExternalSchema, accessExternalDTD, contentForWildcard,
				extension, strict, verbose, debugMode, arguments);

	}

}
