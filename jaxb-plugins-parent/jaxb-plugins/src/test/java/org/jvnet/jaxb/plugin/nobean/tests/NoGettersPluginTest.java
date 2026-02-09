package org.jvnet.jaxb.plugin.nobean.tests;

import com.sun.tools.xjc.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NoGettersPluginTest extends RunXJCMojo {

    @Override
    public File getSchemaDirectory() {
        return new File(getBaseDir(), "src/test/resources");
    }

    @Override
    protected void configureMojo(AbstractXJCMojo<Options> mojo) {
        super.configureMojo(mojo);
        mojo.setForceRegenerate(true);
    }

    @Override
    public List<String> getArgs() {
        final List<String> args = new ArrayList<>(super.getArgs());
        args.add("-XnoGetters");
        return args;
    }

    @Test
    @Override
    public void testExecute() throws Exception {
        super.testExecute();

        List<Path> javaFiles = listGeneratedFiles();

        for (Path javaFile : javaFiles) {
            List<String> lines = Files.readAllLines(javaFile);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.matches("^ *public [a-zA-Z]+ (is|get)[A-Z]\\w+\\(\\) \\{")
                    && !line.matches("^ *public boolean isSet[A-Z]\\w*\\(\\) \\{")) {
                    String fileLinePath = getGeneratedDirectory()
                                              .toPath()
                                              .relativize(javaFile)
                                              .toString()
                                              .replaceFirst(".java$", "")
                                              .replaceAll("/", ".")
                                          + "(" + javaFile.getFileName().toString() + ":" + i + ")";
                    Assertions.fail("One getter remains in file " + fileLinePath + " in the line '" + line + "'");
                }
            }
        }
    }

    private List<Path> listGeneratedFiles() throws IOException {
        try (Stream<Path> list = Files.list(getGeneratedDirectory().toPath()
                                                                   .resolve(Path.of("org/jvnet/jaxb/tests/one")));) {
            return list.collect(Collectors.toList());
        }
    }
}
