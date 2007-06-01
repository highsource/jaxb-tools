/*
 * Copyright [2006] java.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 		http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package org.jvnet.jaxb2.maven2;

import java.io.File;

import junit.framework.TestCase;

import org.apache.maven.plugin.MojoExecutionException;

public class JAXBGenerateTest extends TestCase {

  protected File getBaseDir() {
    try {
      return (new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()))
          .getParentFile()
          .getParentFile()
          .getAbsoluteFile();
    }
    catch (Exception ex) {
      throw new AssertionError(ex);
    }
  }

  /**
   * Validate the generation of a java files from purchaseorder.xsd.
   * @throws MojoExecutionException
   */
  public void testExecute() throws MojoExecutionException {
    XJC2Mojo generator = new XJC2Mojo();
    String userDir = System.getProperty("user.dir").toString();

    generator.setSchemaDirectory(new File(getBaseDir(), "src/test/resources/"));
    generator.setSchemaIncludes(new String[]{ "*.xsd" });
    generator.setBindingIncludes(new String[]{ "*.xjb" });
    generator.setGenerateDirectory(new File(getBaseDir(), "target/test/generated-sources"));
    generator.setVerbose(true);
    generator.setGeneratePackage("unittest");
    generator.setRemoveOldOutput( false);

    generator.execute();

    // Ensure package directory is created
    File[] files = generator.getGenerateDirectory().listFiles();
    assertTrue(files.length >= 1);

    // Ensure four po java files are created.
    files = files[0].listFiles();
    assertTrue(files.length >= 4);
  }

  public static void main(String[] args) throws Exception {
    new JAXBGenerateTest().testExecute();
  }
}
