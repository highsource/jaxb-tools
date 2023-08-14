package org.jvnet.jaxb.maven.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.MatchPatterns;
import org.codehaus.plexus.util.Scanner;
import org.codehaus.plexus.util.SelectorUtils;
import org.jvnet.jaxb.maven.util.CollectionUtils.Function;
import org.sonatype.plexus.build.incremental.BuildContext;
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
			final URL url = file.toURI().toURL();
			return getInputSource(url);
		} catch (MalformedURLException e) {
			return new InputSource(file.getPath());
		}
	}

	public static InputSource getInputSource(final URL url) {
		return new InputSource(StringUtils.escapeSpace(url.toExternalForm()));
	}

	public static InputSource getInputSource(final URI uri) {
		return new InputSource(StringUtils.escapeSpace(uri.toString()));
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

	/**
	 * Scans given directory for files satisfying given inclusion/exclusion
	 * patterns.
	 * 
	 * @param buildContext
	 *            Build context provided by the environment, used to scan for files.
	 * @param directory
	 *            Directory to scan.
	 * @param includes
	 *            inclusion pattern.
	 * @param excludes
	 *            exclusion pattern.
	 * @param defaultExcludes
	 *            default exclusion flag.
	 * @return Files from the given directory which satisfy given patterns. The
	 *         files are {@link File#getCanonicalFile() canonical}.
	 * @throws IOException
	 *             If an I/O error occurs, which is possible because the
	 *             construction of the canonical pathname may require filesystem
	 *             queries.
	 */
	public static List<File> scanDirectoryForFiles(BuildContext buildContext, final File directory,
			final String[] includes, final String[] excludes, boolean defaultExcludes) throws IOException {
		if (!directory.exists()) {
			return Collections.emptyList();
		}
		final Scanner scanner;

		if (buildContext != null) {
			scanner = buildContext.newScanner(directory, true);
		} else {
			final DirectoryScanner directoryScanner = new DirectoryScanner();
			directoryScanner.setBasedir(directory.getAbsoluteFile());
			scanner = directoryScanner;
		}
		scanner.setIncludes(includes);
		scanner.setExcludes(excludes);
		if (defaultExcludes) {
			scanner.addDefaultExcludes();
		}

		scanner.scan();

		// Reorder files according to includes specific file ordering
		List<String> orderedIncludedFiles = reorderFiles(scanner.getIncludedFiles(), includes);

		final List<File> files = new ArrayList<>();
		for (final String name : orderedIncludedFiles) {
			files.add(new File(directory, name).getCanonicalFile());
		}

		return files;
	}

	private static boolean isWildcard (final String s) {
		return s.indexOf ('*') >= 0 || s.indexOf ('?') >= 0;
	}

	/**
	 * Reorder the result of "scanner.getIncludedFile" so that the order of the
	 * source includes is maintained as good as possible.
	 * Examples:<br>
	 * If the includes contain [a, b, c] and the resulting list should be in that order.<br>
	 * If the includes contain [a, b*, c] the resulting list should be [a, matches-of(b*), c]
	 *
	 * @param resolvedFiles
	 *        resolved files from scanner.getIncludedFiles. May not be <code>null</code>.
	 * @param includes
	 *        The source includes in the correct order. May be <code>null</code> or empty.
	 * @return The ordered list of files, that tries to take the source order as good as possible
	 */
	protected static List<String> reorderFiles(final String[] resolvedFiles, final String[] includes) {
		if (includes == null || includes.length == 0) {
			// return "as is"
			return Arrays.asList(resolvedFiles);
		}

		// copy of the initial list in order to avoid changes in resolvedFiles var
		List<String> resolvedFilesList = new ArrayList<>(Arrays.asList(resolvedFiles));
		final List<String> ret = new ArrayList<>(resolvedFilesList.size());

		// For each include
		for (final String include : includes) {
			// Create MatchPatterns from normalizePattern of the include (like it was done in DirectoryScanner)
			MatchPatterns inc = MatchPatterns.from(normalizePattern(include));

			// Find all matches in the resolved files list
			final List<String> matches = new ArrayList<>();
			for (final String resFile : resolvedFilesList) {
				if (inc.matches(resFile, false)) {
					matches.add(resFile);
				}
			}

			// Add all matches to the result list
			ret.addAll(matches);
			// Remove from the main list
			resolvedFilesList.removeAll(matches);
		}

		// Add all remaining resolved files in the order "as is"
		ret.addAll(resolvedFilesList);

		return ret;
	}

	/**
	 * Copy of Plexus-utils function (private) to normalize pattern of include.<br>
	 * Normalizes the pattern, e.g. converts forward and backward slashes to the platform-specific file separator.
	 *
	 * @param pattern The pattern to normalize, must not be <code>null</code>.
	 * @return The normalized pattern, never <code>null</code>.
	 */
	private static String normalizePattern(String pattern)  {
		pattern = pattern.trim();

		if (pattern.startsWith(SelectorUtils.REGEX_HANDLER_PREFIX)) {
			if (File.separatorChar == '\\') {
				pattern = org.codehaus.plexus.util.StringUtils.replace(pattern, "/", "\\\\");
			} else {
				pattern = org.codehaus.plexus.util.StringUtils.replace(pattern, "\\\\", "/");
			}
		} else {
			pattern = pattern.replace(File.separatorChar == '/' ? '\\' : '/', File.separatorChar);

			if (pattern.endsWith(File.separator)) {
				pattern += "**";
			}
		}

		return pattern;
	}
}
