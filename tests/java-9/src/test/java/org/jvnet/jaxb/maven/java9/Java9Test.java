//Copyright (c) 2017 by Disy Informationssysteme GmbH
package org.jvnet.jaxb.maven.java9;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

// NOT_PUBLISHED
public class Java9Test {

  @Test
  public void packageBindingRespected() throws Exception {
    Object o = Class.forName("with_pack.SimpleClassWithPackage").newInstance();
    assertNotNull(o);
  }

  @Test
  public void classNameBindingRespected() throws Exception {
    Object o = Class.forName("class_name.SimpleClassWithRightName").newInstance();
    assertNotNull(o);
  }

}
