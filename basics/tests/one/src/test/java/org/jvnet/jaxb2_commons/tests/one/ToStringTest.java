package org.jvnet.jaxb2_commons.tests.one;

import java.io.File;

import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;

public class ToStringTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {
    
    final Object object = createContext().createUnmarshaller().unmarshal(sample);
    System.out.println(JAXBToStringStrategy.getInstance().append(null, new StringBuilder(), object).toString());
  }

}
