package org.jvnet.jaxb.test.tests;

import java.io.File;

import org.jvnet.jaxb.test.AbstractSamplesTest;

public class TrivialSamplesTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {
    assertTrue("Wrong extension.", sample.getName().endsWith(".xml"));
  }

}
