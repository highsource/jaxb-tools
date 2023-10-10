package org.jvnet.jaxb.tests.qa.simple;

import jakarta.xml.bind.JAXBElement;
import org.jvnet.jaxb.test.AbstractSamplesTest;

import java.io.File;

public class ToStringTest extends AbstractSamplesTest {

  @Override
  protected void checkSample(File sample) throws Exception {

    final Object object = createContext().createUnmarshaller().unmarshal(sample);
    if (object instanceof JAXBElement) {
        System.out.println(((JAXBElement<?>) object).getValue().toString());
    } else {
        System.out.println(object.toString());
    }
  }

}
