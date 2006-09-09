package org.jvnet.jaxb2.maven2.test.tests;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.jvnet.hyperjaxb3.maven2.test.RunXJC2Mojo;

public class FooPluginTest extends RunXJC2Mojo {

  @Override
  public File getSchemaDirectory() {
    return new File(getBaseDir(), "src/test/resources");
  }

  @Override
  public List<String> getArgs() {
    return Collections.singletonList("-Xfoo");
  }

}
