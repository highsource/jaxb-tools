package org.jvnet.jaxb.maven.util;

import org.codehaus.plexus.util.AbstractScanner;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarScanner extends AbstractScanner {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private File destinationDir = new File("/tmp");

    /**
     * The jar artifact to be scanned.
     */
    protected File file;
    /**
     * The files which matched at least one include and no excludes.
     */
    protected ArrayList<String> filesIncluded;
    /**
     * The files which matched no includes or at least one exclude.
     */
    protected ArrayList<String> filesExcluded;

    @Override
    public void scan() {
        if (file == null) {
            throw new IllegalStateException("No file set");
        }
        if (!file.exists()) {
            throw new IllegalStateException("File " + file + " does not exist");
        }
        if (file.isDirectory()) {
            throw new IllegalStateException("File " + file + " is a directory");
        }

        setupDefaultFilters();
        setupMatchPatterns();

        filesIncluded = new ArrayList<>();
        filesExcluded = new ArrayList<>();

        try (JarFile jarFile = new JarFile(file)) {
            final Enumeration<JarEntry> jarFileEntries = jarFile.entries();
            while (jarFileEntries.hasMoreElements()) {
                JarEntry entry = jarFileEntries.nextElement();
                String name = entry.getName();
                File file = new File(destinationDir, entry.getName());
                if (!file.toPath().normalize().startsWith(destinationDir.toPath())) {
                    throw new IOException("Bad zip entry for " + entry.getName());
                }
                char[][] tokenizedName = tokenizePathToCharArray(name, File.separator);
                if (name.endsWith("/")) {
                    // entry is a directory -> skip
                } else if (isIncluded(name, tokenizedName)) {
                    if (!isExcluded(name, tokenizedName)) {
                        filesIncluded.add(name);
                    } else {
                        filesExcluded.add(name);
                    }
                } else {
                    filesExcluded.add(name);
                }
            }
        } catch (IOException ioex) {
            throw new IllegalStateException(
                "Unable to read the artifact JAR file [" + file.getAbsolutePath() + "].", ioex);
        }
    }

    @Override
    public String[] getIncludedFiles() {
        return filesIncluded.toArray(EMPTY_STRING_ARRAY);
    }

    public List<URI> getIncludedURIs() throws URISyntaxException {
        List<URI> uris = new ArrayList<>(filesIncluded.size());
        for (String include : filesIncluded) {
            uris.add(new URI("jar:" + file.toURI() + "!/" + include));
        }
        return uris;
    }

    public String[] getFilesExcluded() {
        return filesExcluded.toArray(EMPTY_STRING_ARRAY);
    }

    @Override
    public String[] getIncludedDirectories() {
        throw new IllegalStateException("JarScanner does not allow to return includedDirectories");
    }

    @Override
    public File getBasedir() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // copy of MatchPattern package-private methods

    static String[] tokenizePathToString(String path, String separator) {
        List<String> ret = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(path, separator);
        while (st.hasMoreTokens()) {
            ret.add(st.nextToken());
        }
        return ret.toArray(new String[0]);
    }

    static char[][] tokenizePathToCharArray(String path, String separator) {
        String[] tokenizedName = tokenizePathToString(path, separator);
        char[][] tokenizedNameChar = new char[tokenizedName.length][];
        for (int i = 0; i < tokenizedName.length; i++) {
            tokenizedNameChar[i] = tokenizedName[i].toCharArray();
        }
        return tokenizedNameChar;
    }
}
