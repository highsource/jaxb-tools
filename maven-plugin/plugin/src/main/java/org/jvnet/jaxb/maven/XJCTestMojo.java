package org.jvnet.jaxb.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;

/**
 * JAXB Test Mojo.
 *
 * Generates sources for testing purpose (ie in /target/generated-test-sources/xjc path).
 *
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@Mojo(name = "generate-test", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES, requiresDependencyResolution = ResolutionScope.TEST, requiresDependencyCollection = ResolutionScope.TEST, threadSafe = true)
public class XJCTestMojo extends XJCMojo {

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
    @Parameter(defaultValue = "${project.build.directory}/generated-test-sources/xjc", property = "maven.xjc2.generateTestDirectory", required = true)
    private File generateDirectory;

    @Override
    public File getGenerateDirectory() {
        return generateDirectory;
    }

    @Override
    public void setGenerateDirectory(File generateDirectory) {
        this.generateDirectory = generateDirectory;

        if (getEpisodeFile() == null) {
            final File episodeFile = new File(getGenerateDirectory(),
                "META-INF" + File.separator + "sun-jaxb.episode");
            setEpisodeFile(episodeFile);
        }
    }

    /**
     * If set to true (default), adds target directory as a compile source root
     * of this Maven project.
     */
    @Parameter(defaultValue = "false", property = "maven.xjc2.addCompileSourceRoot", required = false)
    private boolean addCompileSourceRoot = true;

    @Override
    public boolean getAddCompileSourceRoot() {
        return addCompileSourceRoot;
    }

    @Override
    public void setAddCompileSourceRoot(boolean addCompileSourceRoot) {
        this.addCompileSourceRoot = addCompileSourceRoot;
    }

    /**
     * If set to true, adds target directory as a test compile source root of
     * this Maven project. Default value is false.
     */
    @Parameter(defaultValue = "true", property = "maven.xjc2.addTestCompileSourceRoot", required = false)
    private boolean addTestCompileSourceRoot = false;

    @Override
    public boolean getAddTestCompileSourceRoot() {
        return addTestCompileSourceRoot;
    }

    @Override
    public void setAddTestCompileSourceRoot(boolean addTestCompileSourceRoot) {
        this.addTestCompileSourceRoot = addTestCompileSourceRoot;
    }


    /**
     * The source directory containing *.xsd schema files. Notice that binding
     * files are searched by default in this directory.
     */
    @Parameter(defaultValue = "src/test/resources", property = "maven.xjc2.schemaTestDirectory", required = true)
    private File schemaDirectory;

    @Override
    public File getSchemaDirectory() {
        return schemaDirectory;
    }

    @Override
    public void setSchemaDirectory(File schemaDirectory) {
        this.schemaDirectory = schemaDirectory;
    }
}
