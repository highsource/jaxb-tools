package org.jvnet.hyperjaxb3.ejb.dynamic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.writer.FileCodeWriter;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ErrorReceiver;
import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public class DynamicCompiler {

	private final File targetDirectory;

	private final File[] schemas;

	private final File[] bindings;

	private final File catalog;

	private Outline outline;

	private String contextPath;

	public DynamicCompiler(File[] schemas, File[] bindings, File catalog,
			final File targetDirectory) {
		this.schemas = schemas;
		this.bindings = bindings;
		this.catalog = catalog;
		this.targetDirectory = targetDirectory;
	}

	public ClassLoader getClassLoader() {
		return createClassLoader(Thread.currentThread().getContextClassLoader());
	}

	public ClassLoader createClassLoader(final ClassLoader parentClassLoader) {
		if (outline == null) {
			throw new IllegalStateException("Schemas are not compiled yet.");
		} else {
			try {
				final URL[] urls = new URL[] { targetDirectory.toURI().toURL() };
				final URLClassLoader childClassLoader = new URLClassLoader(
						urls, parentClassLoader);
				return childClassLoader;
			} catch (MalformedURLException ignored) {
				throw new AssertionError(ignored);
			}
		}
	}

	public String getContextPath() {
		if (outline == null) {
			throw new IllegalStateException("Schemas are not compiled yet.");
		} else {
			return contextPath;
		}
	}

	protected static Log logger = LogFactory.getLog(DynamicCompiler.class);

	public Options createOptions() throws IOException, BadCommandLineException {
		final Options options = new Options();
		options.targetDir = targetDirectory;
		options.verbose = true;
		options.debugMode = false;
		options.setSchemaLanguage(Language.XMLSCHEMA);
		options.strictCheck = false;
		options.readOnly = false;
		options.compatibilityMode = Options.EXTENSION;
		// options.set

		if (schemas != null) {
			for (final File schema : schemas) {
				options.addGrammar(schema);
			}
		}

		if (bindings != null) {
			for (File binding : bindings) {
				options.addBindFile(binding);
			}
		}

		if (catalog != null) {
			options.addCatalog(catalog);
		}
		options.parseArguments(getArguments());
		return options;
	}

	public String[] getArguments() {
		return new String[] { "-Xequals", "-XhashCode", "-XtoString",
				"-Xhyperjaxb3-ejb" };
	}

	public JCodeModel createCodeModel() {
		return new JCodeModel();
	}

	public ErrorReceiver createErrorReceiver() {
		return new ConsoleErrorReporter();
	}

	public CodeWriter createCodeWriter() throws IOException {
		return new FileCodeWriter(targetDirectory, false);
	}

	public void execute() throws IOException, BadCommandLineException {

		final Options options = createOptions();
		final JCodeModel codeModel = createCodeModel();
		final CodeWriter codeWriter = createCodeWriter();
		final ErrorReceiver errorReceiver = createErrorReceiver();
		execute(options, codeModel, codeWriter, errorReceiver);

	}

	protected void execute(final Options options, final JCodeModel codeModel,
			final CodeWriter codeWriter, final ErrorReceiver errorReceiver)
			throws IOException {
		logger.debug("Loading the model.");
		final Model model = ModelLoader.load(options, codeModel, errorReceiver);
		logger.debug("Generating the code.");
		final Outline outline = model.generateCode(options, errorReceiver);
		logger.debug("Writing the code.");
		model.codeModel.build(codeWriter);
		this.outline = outline;
		final StringBuffer contextPathStringBuffer = new StringBuffer();
		String delimiter = "";
		for (final PackageOutline packageOutline : this.outline
				.getAllPackageContexts()) {
			contextPathStringBuffer.append(delimiter);
			contextPathStringBuffer.append(packageOutline._package().name());
			delimiter = ":";
		}
		this.contextPath = contextPathStringBuffer.toString();
		logger.debug("Compiling the code.");
		final JavaCompiler systemJavaCompiler = ToolProvider
				.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		final StandardJavaFileManager standardFileManager = systemJavaCompiler
				.getStandardFileManager(diagnostics, null, null);

		final Collection<File> javaFiles = FileUtils.listFiles(targetDirectory,
				FileFilterUtils.suffixFileFilter(".java"),
				TrueFileFilter.INSTANCE);

		final Iterable<? extends JavaFileObject> fileObjects = standardFileManager
				.getJavaFileObjectsFromFiles(javaFiles);

		ClassLoader contextClassLoader = Thread.currentThread()
				.getContextClassLoader();

		final List<String> compilerOptions = new LinkedList<String>();

		if (contextClassLoader instanceof URLClassLoader) {
			final URLClassLoader urlClassLoader = (URLClassLoader) contextClassLoader;
			final URL[] urls = urlClassLoader.getURLs();
			if (urls.length > 0) {
				compilerOptions.add("-classpath");

				final StringBuffer classpathStringBuffer = new StringBuffer();
				String separator = "";
				for (final URL url : urls) {
					logger.debug("URL:" + url);
					classpathStringBuffer.append(separator);
					try {
						classpathStringBuffer.
						// append("\"").
								append(new File(url.toURI()).getAbsolutePath())
						// .append("\"")
						;
						separator = SystemUtils.PATH_SEPARATOR;
					} catch (URISyntaxException ignored) {
					}
				}
				compilerOptions.add(classpathStringBuffer.toString());
			}
		}
		compilerOptions.add("-verbose");
		compilerOptions.add("-d");
		compilerOptions.add(targetDirectory.getAbsolutePath());

		logger.debug("Compiler options:"
				+ StringUtils.join(compilerOptions.iterator(), " "));
		// // conte
		//
		// final String[] compilerOptions = new String[] { "-d",
		// targetDirectory.getAbsolutePath(), "-verbose" };

		systemJavaCompiler.getTask(null, standardFileManager, null,
				compilerOptions, null, fileObjects).call();
		standardFileManager.close();
	}
}
