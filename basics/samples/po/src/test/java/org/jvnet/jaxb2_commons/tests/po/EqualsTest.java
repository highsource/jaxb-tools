package org.jvnet.jaxb2_commons.tests.po;

import java.io.File;

import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class EqualsTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {

    final Object lhs = createContext().createUnmarshaller().unmarshal(sample);
    final Object rhs = createContext().createUnmarshaller().unmarshal(sample);
    assertTrue("Values must be equal.", JAXBEqualsStrategy.INSTANCE.equals(null, null, lhs, rhs));
  }
}
