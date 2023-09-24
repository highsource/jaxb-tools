package org.jvnet.jaxb2_commons.tests.one;

import java.io.File;

import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.test.AbstractSamplesTest;

public class ToStringTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {

    final Object object = createContext().createUnmarshaller().unmarshal(sample);
    System.out.println(JAXBToStringStrategy.getInstance().append(null, new StringBuilder(), object).toString());
  }

}
