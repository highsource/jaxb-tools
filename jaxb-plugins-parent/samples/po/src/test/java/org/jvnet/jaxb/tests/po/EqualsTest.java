package org.jvnet.jaxb.tests.po;

import java.io.File;

import org.junit.jupiter.api.Assertions;

import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.test.AbstractSamplesTest;

public class EqualsTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {

    final Object lhs = createContext().createUnmarshaller().unmarshal(sample);
    final Object rhs = createContext().createUnmarshaller().unmarshal(sample);
    Assertions.assertTrue(JAXBEqualsStrategy.INSTANCE.equals(null, null, lhs, rhs), "Values must be equal.");
  }
}
