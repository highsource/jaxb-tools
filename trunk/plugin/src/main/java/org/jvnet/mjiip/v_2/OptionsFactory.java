package org.jvnet.mjiip.v_2;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.jvnet.jaxb2.maven2.OptionsConfiguration;
import org.jvnet.jaxb2.maven2.util.StringUtils;
import org.xml.sax.InputSource;

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

		options.target = createSpecVersion(optionsConfiguration
				.getSpecVersion());

		final String encoding = optionsConfiguration.getEncoding();
		if (encoding != null) {
			options.encoding = createEncoding(encoding);
		}

		options.setSchemaLanguage(createLanguage(optionsConfiguration
				.getSchemaLanguage()));

		for (InputSource grammar : optionsConfiguration.getGrammars()) {
			options.addGrammar(grammar);
		}

		for (InputSource bindFile : optionsConfiguration.getBindFiles()) {
			options.addBindFile(bindFile);
		}

		options.entityResolver = optionsConfiguration.getCatalogResolver();

		for (URL catalog : optionsConfiguration.getCatalogs()) {
			if (catalog != null) {
				try {
					if (options.entityResolver == null) {
						CatalogManager.getStaticManager()
								.setIgnoreMissingProperties(true);
						options.entityResolver = new CatalogResolver(true);
					}
					((CatalogResolver) options.entityResolver).getCatalog()
							.parseCatalog(catalog);
					// options.addCatalog(catalog);
				} catch (IOException ioex) {
					throw new MojoExecutionException(MessageFormat.format(
							"Error parsing catalog [{0}].",
							catalog.toExternalForm()), ioex);
				}
			}
		}

		// Setup Other Options

		options.defaultPackage = optionsConfiguration.getGeneratePackage();
		options.targetDir = optionsConfiguration.getGenerateDirectory();

		options.strictCheck = optionsConfiguration.isStrict();
		options.readOnly = optionsConfiguration.isReadOnly();

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

	private SpecVersion createSpecVersion(String specVersion) {
		if (specVersion == null) {
			return SpecVersion.LATEST;
		} else {
			final SpecVersion sv = SpecVersion.parse(specVersion);
			return sv == null ? SpecVersion.LATEST : sv;
		}

	}

	private String createEncoding(String encoding)
			throws MojoExecutionException {
		if (encoding == null) {
			return null;
		}
		try {
			if (!Charset.isSupported(encoding)) {
				throw new MojoExecutionException(

				MessageFormat.format("Unsupported encoding [{0}].", encoding));
			}
			return encoding;
		} catch (IllegalCharsetNameException icne) {
			throw new MojoExecutionException(

			MessageFormat.format("Unsupported encoding [{0}].", encoding));
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
			throw new MojoExecutionException(MessageFormat.format(
					"Unknown schemaLanguage [{0}].", schemaLanguage));
		}
	}
}
