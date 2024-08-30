package org.jvnet.jaxb.test.tests;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.jvnet.jaxb.test.AbstractSamplesTest;

public class TrivialSamplesTest extends AbstractSamplesTest {

    @Override
    protected void checkSample(File sample) throws Exception {
        Assertions.assertTrue(sample.getName().endsWith(".xml"), "Wrong extension.");
    }

}
