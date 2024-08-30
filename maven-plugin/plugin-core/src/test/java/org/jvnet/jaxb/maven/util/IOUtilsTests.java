package org.jvnet.jaxb.maven.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class IOUtilsTests {

    @Test
    public void reorderFilesIncludesNull() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, null);

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");
        Assertions.assertEquals(files[0], orderedFiles.get(0));
        Assertions.assertEquals(files[1], orderedFiles.get(1));
        Assertions.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesEmpty() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] {});

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");
        Assertions.assertEquals(files[0], orderedFiles.get(0));
        Assertions.assertEquals(files[1], orderedFiles.get(1));
        Assertions.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesNoWildcard() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "b.xsd", "c.xsd", "a.xsd" });

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");
        Assertions.assertEquals(files[2], orderedFiles.get(0));
        Assertions.assertEquals(files[1], orderedFiles.get(1));
        Assertions.assertEquals(files[0], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesNoWildcardWithCommonSuffix() {
        String[] files = {"a.xsd", "b.xsd", "service-ab.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "b.xsd", "a.xsd", "service-ab.xsd" });

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");
        Assertions.assertEquals(files[1], orderedFiles.get(0));
        Assertions.assertEquals(files[0], orderedFiles.get(1));
        Assertions.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesWithWildcardFirst() {
        String[] files = {"a.xsd", "common" + File.separatorChar + "c1.xsd", "b.xsd", "common" + File.separatorChar + "c2.xsd", "common" + File.separatorChar + "a.xsd", "common" + File.separatorChar + "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "common/*.xsd", "a.xsd", "b.xsd" });

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");
        // we have all common/*.xsd files in same order
        Assertions.assertEquals(files[1], orderedFiles.get(0));
        Assertions.assertEquals(files[3], orderedFiles.get(1));
        Assertions.assertEquals(files[4], orderedFiles.get(2));
        Assertions.assertEquals(files[5], orderedFiles.get(3));
        // and then a.xsd
        Assertions.assertEquals(files[0], orderedFiles.get(4));
        // and finally b.xsd
        Assertions.assertEquals(files[2], orderedFiles.get(5));
    }

    @Test
    public void reorderFilesIncludesWithWildcardMiddle() {
        String[] files = {"a.xsd", "common" + File.separatorChar + "c1.xsd", "b.xsd", "common" + File.separatorChar + "c2.xsd", "common" + File.separatorChar + "a.xsd", "common" + File.separatorChar + "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "a.xsd", "common/*.xsd", "b.xsd" });

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");

        // we have a.xsd
        Assertions.assertEquals(files[0], orderedFiles.get(0));
        // and then all common/*.xsd files in same order
        Assertions.assertEquals(files[1], orderedFiles.get(1));
        Assertions.assertEquals(files[3], orderedFiles.get(2));
        Assertions.assertEquals(files[4], orderedFiles.get(3));
        Assertions.assertEquals(files[5], orderedFiles.get(4));
        // and finally b.xsd
        Assertions.assertEquals(files[2], orderedFiles.get(5));
    }

    @Test
    public void reorderFilesIncludesWithWildcardLast() {
        String[] files = {"a.xsd", "common" + File.separatorChar + "c1.xsd", "b.xsd", "common" + File.separatorChar + "c2.xsd", "common" + File.separatorChar + "a.xsd", "common" + File.separatorChar + "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "a.xsd", "b.xsd", "common/*.xsd" });

        Assertions.assertNotNull(orderedFiles,
            "Ordered files list should not be null");
        Assertions.assertEquals(files.length, orderedFiles.size(),
            "Ordered files list should contains all elements of initial list");

        // we have a.xsd
        Assertions.assertEquals(files[0], orderedFiles.get(0));
        // and then b.xsd
        Assertions.assertEquals(files[2], orderedFiles.get(1));
        // and finally all common/*.xsd files in same order
        Assertions.assertEquals(files[1], orderedFiles.get(2));
        Assertions.assertEquals(files[3], orderedFiles.get(3));
        Assertions.assertEquals(files[4], orderedFiles.get(4));
        Assertions.assertEquals(files[5], orderedFiles.get(5));
    }
}
