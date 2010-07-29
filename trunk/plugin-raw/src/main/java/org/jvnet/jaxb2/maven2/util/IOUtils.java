package org.jvnet.jaxb2.maven2.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.DirectoryScanner;
import org.jvnet.jaxb2.maven2.util.CollectionUtils.Function;
import org.xml.sax.InputSource;

public class IOUtils {

	/**
	 * Creates an input source for the given file.
	 * 
	 * @param file
	 *            file to create input source for.
	 * 
	 * @return Created input source object.
	 */
	public static InputSource getInputSource(File file) {
		try {
			return new InputSource(StringUtils.escapeSpace(file.toURI().toURL()
					.toExternalForm()));
		} catch (MalformedURLException e) {
			return new InputSource(file.getPath());
		}
	}

	public static final Function<File, URL> GET_URL = new Function<File, URL>() {
		public URL eval(File file) {
			try {
				return file.toURI().toURL();
			} catch (MalformedURLException muex) {
				throw new RuntimeException(muex);
			}
		}
	};
	
	public static final Function<File, Long> LAST_MODIFIED = new Function<File, Long>() {
		public Long eval(File file) {
			return lastModified(file);
		}
	};

	public static long lastModified(File file) {
		if (file == null || !file.exists()) {
			return 0;
		} else {
			return file.lastModified();
		}
	}



	/**
	 * Scans given directory for files satisfying given inclusion/exclusion
	 * patterns.
	 * 
	 * @param directory
	 *            Directory to scan.
	 * @param includes
	 *            inclusion pattern.
	 * @param excludes
	 *            exclusion pattern.
	 * @param defaultExcludes
	 *            default exclusion flag.
	 * @return Files from the given directory which satisfy given patterns. The files are {@link File#getCanonicalFile() canonical}.
	 */
	public static List<File> scanDirectoryForFiles(final File directory,
			final String[] includes, final String[] excludes,
			boolean defaultExcludes) throws IOException {
		final DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(directory.getAbsoluteFile());
		scanner.setIncludes(includes);
		scanner.setExcludes(excludes);
		if (defaultExcludes) {
			scanner.addDefaultExcludes();
		}
	
		scanner.scan();
	
		final List<File> files = new ArrayList<File>();
		for (final String name : scanner.getIncludedFiles()) {
			files.add(new File(directory, name).getCanonicalFile());
		}
	
		return files;
	}
	
	

}
