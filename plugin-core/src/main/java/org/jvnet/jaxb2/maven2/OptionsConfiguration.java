package org.jvnet.jaxb2.maven2;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class OptionsConfiguration {

	private final String encoding;

	private final String schemaLanguage;
	private List<InputSource> grammars;
	private List<InputSource> bindFiles;

	private final EntityResolver entityResolver;

	private final String generatePackage;

	private final File generateDirectory;

	private final boolean readOnly;

	private final boolean packageLevelAnnotations;
	private final boolean noFileHeader;
	private final boolean enableIntrospection;
	private final boolean disableXmlSecurity;
	private final String accessExternalSchema;
	private final String accessExternalDTD;
	private final boolean enableExternalEntityProcessing;
	private final boolean contentForWildcard;

	private final boolean extension;

	private final boolean strict;

	private final boolean verbose;

	private final boolean debugMode;

	private final List<String> arguments;

	private final List<URL> plugins;

	private final String specVersion;

	public OptionsConfiguration(String encoding, String schemaLanguage,
			List<InputSource> grammars, List<InputSource> bindFiles,
			EntityResolver entityResolver, String generatePackage,
			File generateDirectory, boolean readOnly,
			boolean packageLevelAnnotations, boolean noFileHeader,
			boolean enableIntrospection, boolean disableXmlSecurity,
			String accessExternalSchema, String accessExternalDTD,
			boolean enableExternalEntityProcessing,
			boolean contentForWildcard,
			boolean extension, boolean strict, boolean verbose,
			boolean debugMode, List<String> arguments, List<URL> plugins,
			String specVersion) {
		super();
		this.encoding = encoding;
		this.schemaLanguage = schemaLanguage;
		this.grammars = grammars;
		this.bindFiles = bindFiles;
		this.entityResolver = entityResolver;
		this.generatePackage = generatePackage;
		this.generateDirectory = generateDirectory;
		this.readOnly = readOnly;
		this.packageLevelAnnotations = packageLevelAnnotations;
		this.noFileHeader = noFileHeader;
		this.enableIntrospection = enableIntrospection;
		this.disableXmlSecurity = disableXmlSecurity;
		this.accessExternalSchema = accessExternalSchema;
		this.accessExternalDTD = accessExternalDTD;
		this.enableExternalEntityProcessing = enableExternalEntityProcessing;
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

	public List<InputSource> getGrammars() {
		return grammars;
	}

	public List<InputSource> getBindFiles() {
		return bindFiles;
	}

	public EntityResolver getEntityResolver() {
		return entityResolver;
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
	
	public boolean isEnableExternalEntityProcessing() {
		return enableExternalEntityProcessing;
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
		return MessageFormat.format(
				"OptionsConfiguration [" +
				//

						"specVersion={0}\n " +
						//
						"generateDirectory={1}\n " +
						//
						"generatePackage={2}\n " +
						//
						"schemaLanguage={3}\n " +
						//
						"grammars.systemIds={4}\n " +
						//
						"bindFiles.systemIds={5}\n " +
						//
						"plugins={6}\n " +
						//
						"readOnly={7}\n " +
						//
						"packageLevelAnnotations={8}\n " +
						//
						"noFileHeader={9}\n " +
						//
						"enableIntrospection={10}\n " +
						//
						"disableXmlSecurity={11}\n " +
						//
						"accessExternalSchema={12}\n " +
						//
						"accessExternalDTD={13}\n " +
						//
						"contentForWildcard={14}\n " +
						//
						"extension={15}\n " +
						//
						"strict={16}\n " +
						//
						"verbose={17}\n " +
						//
						"debugMode={18}\n " +
						//
						"arguments={19}" +
						//
						"]",
				// 0
				specVersion, generateDirectory, generatePackage,
				schemaLanguage,
				getSystemIds(grammars),
				// 5
				getSystemIds(bindFiles), plugins, readOnly,
				packageLevelAnnotations, noFileHeader,
				// 10
				enableIntrospection, disableXmlSecurity, accessExternalSchema,
				accessExternalDTD, contentForWildcard,
				// 15
				extension, strict, verbose, debugMode, arguments);

	}

	private List<String> getSystemIds(List<InputSource> inputSources) {
		if (inputSources == null) {
			return null;
		} else {
			final List<String> systemIds = new ArrayList<String>(
					inputSources.size());
			for (InputSource inputSource : inputSources) {
				systemIds.add(inputSource == null ? null : inputSource
						.getSystemId());
			}
			return systemIds;
		}
	}

}
