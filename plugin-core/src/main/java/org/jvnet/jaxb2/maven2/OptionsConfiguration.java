package org.jvnet.jaxb2.maven2;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jvnet.jaxb2.maven2.util.IOUtils;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;

public class OptionsConfiguration {

	private final String schemaLanguage;
	private final List<URL> schemas;
	private final List<URL> bindings;
	private final List<URL> catalogs;

	private final CatalogResolver catalogResolver;

	private final String generatePackage;

	private final File generateDirectory;

	private final boolean readOnly;

	private final boolean extension;

	private final boolean strict;

	private final boolean verbose;

	private final boolean debugMode;

	private final List<String> arguments;

	private final List<URL> plugins;

	private final String specVersion;

	public OptionsConfiguration(String schemaLanguage, List<URL> schemas,
			List<URL> bindings, List<URL> catalogs, CatalogResolver catalogResolver,
			String generatePackage, File generateDirectory, boolean readOnly,
			boolean extension, boolean strict, boolean verbose,
			boolean debugMode, List<String> arguments, List<URL> plugins,
			String specVersion) {
		super();
		this.schemaLanguage = schemaLanguage;
		this.schemas = schemas;
		this.bindings = bindings;
		this.catalogs = catalogs;
		this.catalogResolver = catalogResolver;
		this.generatePackage = generatePackage;
		this.generateDirectory = generateDirectory;
		this.readOnly = readOnly;
		this.extension = extension;
		this.strict = strict;
		this.verbose = verbose;
		this.debugMode = debugMode;
		this.arguments = arguments;
		this.plugins = plugins;
		this.specVersion = specVersion;
	}

	public String getSchemaLanguage() {
		return schemaLanguage;
	}

	public List<URL> getSchemas() {
		return schemas;
	}

	public List<InputSource> getGrammars() {
		final List<URL> schemas = getSchemas();
		final List<InputSource> grammars = new ArrayList<InputSource>(
				schemas.size());
		for (final URL schema : schemas) {
			grammars.add(IOUtils.getInputSource(schema));
		}
		return grammars;
	}

	public List<URL> getBindings() {
		return bindings;
	}

	public List<InputSource> getBindFiles() {
		final List<URL> bindings = getBindings();
		final List<InputSource> bindFiles = new ArrayList<InputSource>(
				bindings.size());
		for (final URL binding : bindings) {
			bindFiles.add(IOUtils.getInputSource(binding));
		}
		return bindFiles;
	}

	public List<URL> getCatalogs() {
		return catalogs;
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
				"extension={10}\n " +
				//
				"strict={11}\n " +
				//
				"verbose={12}\n " +
				//
				"debugMode={13}\n " +
				//
				"arguments={14}" +
				//
				"]", specVersion, generateDirectory, generatePackage,
				schemaLanguage, schemas, bindings, plugins, catalogs,
				catalogResolver, readOnly, extension, strict, verbose,
				debugMode, arguments);
	}

}
