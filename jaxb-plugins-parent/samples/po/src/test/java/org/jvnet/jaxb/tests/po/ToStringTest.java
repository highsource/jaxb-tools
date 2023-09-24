package org.jvnet.jaxb.tests.po;

import java.io.File;

import org.jvnet.jaxb.test.AbstractSamplesTest;
import org.jvnet.jaxb.lang.JAXBToStringStrategy;

public class ToStringTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {

    final Object object = createContext().createUnmarshaller().unmarshal(sample);
    logger.debug(JAXBToStringStrategy.INSTANCE.append(null, new StringBuilder(), object).toString());
  }

}
