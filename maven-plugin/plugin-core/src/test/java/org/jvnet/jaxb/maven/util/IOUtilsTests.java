package org.jvnet.jaxb.maven.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOUtilsTests {

    @Test
    public void reorderFilesIncludesNull() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, null);

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());
        Assert.assertEquals(files[0], orderedFiles.get(0));
        Assert.assertEquals(files[1], orderedFiles.get(1));
        Assert.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesEmpty() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] {});

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());
        Assert.assertEquals(files[0], orderedFiles.get(0));
        Assert.assertEquals(files[1], orderedFiles.get(1));
        Assert.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesNoWildcard() {
        String[] files = {"a.xsd", "c.xsd", "b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "b.xsd", "c.xsd", "a.xsd" });

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());
        Assert.assertEquals(files[2], orderedFiles.get(0));
        Assert.assertEquals(files[1], orderedFiles.get(1));
        Assert.assertEquals(files[0], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesNoWildcardWithCommonSuffix() {
        String[] files = {"a.xsd", "b.xsd", "service-ab.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "b.xsd", "a.xsd", "service-ab.xsd" });

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());
        Assert.assertEquals(files[1], orderedFiles.get(0));
        Assert.assertEquals(files[0], orderedFiles.get(1));
        Assert.assertEquals(files[2], orderedFiles.get(2));
    }

    @Test
    public void reorderFilesIncludesWithWildcardFirst() {
        String[] files = {"a.xsd", "common/c1.xsd", "b.xsd", "common/c2.xsd", "common/a.xsd", "common/b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "common/*.xsd", "a.xsd", "b.xsd" });

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());
        // we have all common/*.xsd files in same order
        Assert.assertEquals(files[1], orderedFiles.get(0));
        Assert.assertEquals(files[3], orderedFiles.get(1));
        Assert.assertEquals(files[4], orderedFiles.get(2));
        Assert.assertEquals(files[5], orderedFiles.get(3));
        // and then a.xsd
        Assert.assertEquals(files[0], orderedFiles.get(4));
        // and finally b.xsd
        Assert.assertEquals(files[2], orderedFiles.get(5));
    }

    @Test
    public void reorderFilesIncludesWithWildcardMiddle() {
        String[] files = {"a.xsd", "common/c1.xsd", "b.xsd", "common/c2.xsd", "common/a.xsd", "common/b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "a.xsd", "common/*.xsd", "b.xsd" });

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());

        // we have a.xsd
        Assert.assertEquals(files[0], orderedFiles.get(0));
        // and then all common/*.xsd files in same order
        Assert.assertEquals(files[1], orderedFiles.get(1));
        Assert.assertEquals(files[3], orderedFiles.get(2));
        Assert.assertEquals(files[4], orderedFiles.get(3));
        Assert.assertEquals(files[5], orderedFiles.get(4));
        // and finally b.xsd
        Assert.assertEquals(files[2], orderedFiles.get(5));
    }

    @Test
    public void reorderFilesIncludesWithWildcardLast() {
        String[] files = {"a.xsd", "common/c1.xsd", "b.xsd", "common/c2.xsd", "common/a.xsd", "common/b.xsd" };
        List<String> orderedFiles = IOUtils.reorderFiles(files, new String[] { "a.xsd", "b.xsd", "common/*.xsd" });

        Assert.assertNotNull("Ordered files list should not be null",
                orderedFiles);
        Assert.assertEquals("Ordered files list should contains all elements of initial list",
                files.length, orderedFiles.size());

        // we have a.xsd
        Assert.assertEquals(files[0], orderedFiles.get(0));
        // and then b.xsd
        Assert.assertEquals(files[2], orderedFiles.get(1));
        // and finally all common/*.xsd files in same order
        Assert.assertEquals(files[1], orderedFiles.get(2));
        Assert.assertEquals(files[3], orderedFiles.get(3));
        Assert.assertEquals(files[4], orderedFiles.get(4));
        Assert.assertEquals(files[5], orderedFiles.get(5));
    }
}
