package org.jvnet.jaxb2.maven2.test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.Mojo;
import org.jvnet.jaxb2.maven2.XJC2Mojo;
import org.jvnet.jaxb2.maven2.XJC2Mojo;

/**
 * Abstract test for plugins.
 * 
 * @author Aleksei Valikov
 */

public class RunXJC2Mojo extends TestCase {
  /**
   * Logger.
   */
  protected Log log = LogFactory.getLog(RunXJC2Mojo.class);

  public void testExecute() throws Exception {
    final Mojo mojo = initMojo();
    mojo.execute();
  }
  
  public void check() throws Exception
  {    
  }

  protected File getBaseDir() {
    try {
      return (new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()))
          .getParentFile()
          .getParentFile()
          .getAbsoluteFile();
    }
    catch (Exception ex) {
      throw new AssertionError(ex);
    }
  }

  public File getSchemaDirectory() {
    return new File(getBaseDir(), "src/main/resources");
  }

  protected File getGeneratedDirectory() {
    return new File(getBaseDir(), "target/generated-sources/xjc");
  }

  public List<String> getArgs() {
    return Collections.emptyList();
  }

  public String getGeneratePackage() {
    return null;
  }

  public boolean isWriteCode() {
    return true;
  }

  public XJC2Mojo initMojo() {
    final XJC2Mojo mojo = createMojo();
    configureMojo(mojo);
    return mojo;
  }

  protected XJC2Mojo createMojo() {
    return new XJC2Mojo();
  }

  protected void configureMojo(final XJC2Mojo mojo) {
    mojo.setSchemaDirectory(getSchemaDirectory());
    mojo.setGenerateDirectory(getGeneratedDirectory());
    mojo.setGeneratePackage(getGeneratePackage());
    mojo.setArgs(getArgs());
    mojo.setVerbose(true);
    mojo.setDebug(true);
    mojo.setWriteCode(isWriteCode());
  }
}