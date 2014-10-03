package org.jvnet.mjiip.v_2_2;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.jvnet.jaxb2.maven2.OptionsConfiguration;
import org.jvnet.jaxb2.maven2.resolver.tools.ReResolvingEntityResolverWrapper;
import org.jvnet.jaxb2.maven2.util.StringUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.api.SpecVersion;

public class OptionsFactory implements
		org.jvnet.jaxb2.maven2.OptionsFactory<Options> {
	/**
	 * Creates and initializes an instance of XJC options.
	 * 
	 */
	public Options createOptions(OptionsConfiguration optionsConfiguration)
			throws MojoExecutionException {
		final Options options = new Options();

		options.verbose = optionsConfiguration.isVerbose();
		options.debugMode = optionsConfiguration.isDebugMode();

		options.classpaths.addAll(optionsConfiguration.getPlugins());

		options.target = SpecVersion.V2_2;

		final String encoding = optionsConfiguration.getEncoding();
		if (encoding != null) {
			options.encoding = createEncoding(encoding);
		}
		options.setSchemaLanguage(createLanguage(optionsConfiguration
				.getSchemaLanguage()));

		CatalogResolver catalogResolver = optionsConfiguration
				.getCatalogResolver();

		// Setup Catalog files (XML Entity Resolver).
		for (URI catalog : optionsConfiguration.getCatalogs()) {
			if (catalog != null) {
				try {
					if (catalogResolver == null) {
						CatalogManager.getStaticManager()
								.setIgnoreMissingProperties(true);
						catalogResolver = new CatalogResolver(true);
					}
					catalogResolver.getCatalog().parseCatalog(catalog.toURL());
					// options.addCatalog(catalog);
				} catch (IOException ioex) {
					throw new MojoExecutionException(MessageFormat.format(
							"Error parsing catalog [{0}].",
							catalog.toString()), ioex);
				}
			}
		}
		final EntityResolver entityResolver = new ReResolvingEntityResolverWrapper(
				catalogResolver);

		options.entityResolver = entityResolver;

		try {
			for (InputSource grammar : optionsConfiguration
					.getGrammars(entityResolver)) {
				options.addGrammar(grammar);
			}
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not resolve grammars.",
					ioex);
		} catch (SAXException ioex) {
			throw new MojoExecutionException("Could not resolve grammars.",
					ioex);
		}

		try {
			for (InputSource bindFile : optionsConfiguration
					.getBindFiles(entityResolver)) {
				options.addBindFile(bindFile);
			}
		} catch (IOException ioex) {
			throw new MojoExecutionException("Could not resolve grammars.",
					ioex);
		} catch (SAXException ioex) {
			throw new MojoExecutionException("Could not resolve grammars.",
					ioex);
		}

		// Setup Other Options

		options.defaultPackage = optionsConfiguration.getGeneratePackage();
		options.targetDir = optionsConfiguration.getGenerateDirectory();

		options.strictCheck = optionsConfiguration.isStrict();
		options.readOnly = optionsConfiguration.isReadOnly();
		options.packageLevelAnnotations = optionsConfiguration
				.isPackageLevelAnnotations();
		options.noFileHeader = optionsConfiguration.isNoFileHeader();
		options.enableIntrospection = optionsConfiguration
				.isEnableIntrospection();
		options.disableXmlSecurity = optionsConfiguration
				.isDisableXmlSecurity();
		if (optionsConfiguration.getAccessExternalSchema() != null) {
			System.setProperty("javax.xml.accessExternalSchema",
					optionsConfiguration.getAccessExternalSchema());
		}
		if (optionsConfiguration.getAccessExternalDTD() != null) {
			System.setProperty("javax.xml.accessExternalDTD",
					optionsConfiguration.getAccessExternalDTD());
		}
		options.contentForWildcard = optionsConfiguration
				.isContentForWildcard();

		if (optionsConfiguration.isExtension()) {
			options.compatibilityMode = Options.EXTENSION;
		}

		final List<String> arguments = optionsConfiguration.getArguments();
		try {
			options.parseArguments(arguments.toArray(new String[arguments
					.size()]));
		}

		catch (BadCommandLineException bclex) {
			throw new MojoExecutionException("Error parsing the command line ["
					+ arguments + "]", bclex);
		}

		return options;
	}

	private String createEncoding(String encoding)
			throws MojoExecutionException {
		if (encoding == null) {
			return null;
		}
		try {
			if (!Charset.isSupported(encoding)) {
				throw new MojoExecutionException(MessageFormat.format(
						"Unsupported encoding [{0}].", encoding));
			}
			return encoding;
		} catch (IllegalCharsetNameException icne) {
			throw new MojoExecutionException(MessageFormat.format(
					"Unsupported encoding [{0}].", encoding));
		}

	}

	private Language createLanguage(String schemaLanguage)
			throws MojoExecutionException {
		if (StringUtils.isEmpty(schemaLanguage)) {
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
					+ schemaLanguage + "].");
		}
	}
}
