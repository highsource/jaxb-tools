package org.jvnet.jaxb2_commons.tests.zj;

import java.io.File;

import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class EqualsTest extends AbstractSamplesTest {

  @Override
  public String getContextPath() {
    return "com.oce.obis.sei.api.data";
  }

  @Override
  protected void checkSample(File sample) throws Exception {
    final Object lhs = createContext().createUnmarshaller().unmarshal(sample);
    final Object rhs = createContext().createUnmarshaller().unmarshal(sample);
    assertTrue("Values must be equal.", JAXBEqualsStrategy.getInstance().equals(null, null, lhs, rhs));
  }
}
