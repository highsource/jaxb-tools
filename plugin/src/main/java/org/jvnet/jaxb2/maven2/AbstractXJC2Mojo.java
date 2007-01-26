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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.ErrorReceiver;
import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

/**
 * The parent of all mojos that uses JAXB 1.x XJC compiler to compile
 * schemas (XML schemas, DTD, WSDL, or RELAXNG) into...anything. 
 * For details on JAXB see <a href="https://jaxb.dev.java.net/1.0/">JAXB 1.x Project</a>.
 * 
 * @author Kostis Anagnostopoulos (ankostis@mail.com)
 */
public abstract class AbstractXJC2Mojo extends AbstractMojo {

  /**
   * For checking timestamps. Modify freely, nothing to do with XJC options.
   */
  protected List schemaFiles = new ArrayList();

  /**
   * For checking timestamps. Modify freely, nothing to do with XJC options.
   */
  protected List bindingFiles = new ArrayList();

  /**
   * Execute the maven2 mojo to invoke the xjc2 compiler based on any 
   * configuration settings.
   */
  protected void executeImp() throws MojoExecutionException {
    try {
      setupLogging();

      // Translate maven plugin options to XJC ones, 
      // also perform any sanity checks.
      // SIDE-EFFECT: populates schemaFiles and bindingFiles member vars.
      Options xjcOpts = setupOptions();

      if (isVerbose()) {
        logSettings();
      }

      // Add source path and jaxb resources whether re-generate or not.
      updateMavenPaths();

      // Check whether to re-generate sources.
      if (!this.isForceRegenerate() && isUpdToDate()) {
        getLog().info("Skipped XJC execution.  Generated sources were up-to-date.");
        return;
      }

      // Remove old generated dir.
      if (this.isRemoveOldOutput()) {
        if (this.getGenerateDirectory().exists()) {
          try {
            FileUtils.deleteDirectory(this.getGenerateDirectory());
            getLog().info("Removed old generateDirectory '" + this.getGenerateDirectory() + "'.");
          }
          catch (IOException ex) {
            getLog().warn(
                "Failed to remove old generateDirectory '"
                    + this.getGenerateDirectory()
                    + "' due to: "
                    + ex);
          }

        }
        else if (isVerbose())
          getLog().info(
              "Skipped removal of old generateDirectory '"
                  + this.getGenerateDirectory()
                  + "' since it didn't exist.");
      }
      // Create the destination path if it does not exist.
      if (getGenerateDirectory() != null && !getGenerateDirectory().exists()) {
        getGenerateDirectory().mkdirs();
      }

      // Install project dependencies into classloader's class path
      //  and execute xjc2.
      ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(getProjectDepsClassLoader(oldCL));
      try {
        runXJC(xjcOpts);
      }
      finally {
        // Set back the old classloader
        Thread.currentThread().setContextClassLoader(oldCL);
      }

      // Inform user about completion.
      getLog().info("Succesfully generated output to: " + xjcOpts.targetDir);

    }
    catch (RuntimeException ex) {
      getLog().info("Mojo options will be logged due to an unexpected error...");
      logSettings(); // for easy debugging.
      throw ex;
    }
    catch (MojoExecutionException ex) {
      throw ex;
    }
  }

  /**
   * Ensure the any default settings are met and throws exceptions when
   * settings are invalid and also stores the schemas and the bindings files 
   * into member vars for calculating timestamps, later. 
   * 
   * Exception will cause build to fail.
   * 
   * @throws MojoExecutionException
   */
  protected Options setupOptions() throws MojoExecutionException {
    // DISCOVER XJC ADD-ONS HERE
    // MUST COME AFTER setting "com.sun.tools.xjc.Options.findServices"
    // since this property should be present during classloading.
    Options xjcOpts = new Options();

    xjcOpts.verbose = this.isVerbose();
    xjcOpts.debugMode = this.isDebug();

    // Setup Schema Language.
    if (!isDefined(getSchemaLanguage(), 1)) {
      setSchemaLanguage("AUTODETECT");
      if (isVerbose())
        getLog().info("The <schemaLanguage> setting was not defined, assuming 'AUTODETECT'.");
    }
    else if ("AUTODETECT".equalsIgnoreCase(getSchemaLanguage()))
      ; // nothing, it is AUTDETECT by default.
    else if ("XMLSCHEMA".equalsIgnoreCase(getSchemaLanguage()))
      xjcOpts.setSchemaLanguage(Language.XMLSCHEMA);
    else if ("DTD".equalsIgnoreCase(getSchemaLanguage()))
      xjcOpts.setSchemaLanguage(Language.DTD);
    else if ("RELAXNG".equalsIgnoreCase(getSchemaLanguage()))
      xjcOpts.setSchemaLanguage(Language.RELAXNG);
    else if ("RELAXNG_COMPACT".equalsIgnoreCase(getSchemaLanguage()))
      xjcOpts.setSchemaLanguage(Language.RELAXNG_COMPACT);
    else if ("WSDL".equalsIgnoreCase(getSchemaLanguage()))
      xjcOpts.setSchemaLanguage(Language.WSDL);
    else {
      logSettings(); // for easy debugging.
      throw new MojoExecutionException("Unknown <schemaLanguage> '"
          + this.getSchemaLanguage()
          + "'!");
    }

    List files;
    Iterator it;

    files = gatherFiles(this.getSchemaDirectory(), this.getSchemaIncludes(), this
        .getSchemasExcludes());

    it = files.iterator();
    for (; it.hasNext();)
      xjcOpts.addGrammar(getInputSource((File) it.next()));

    this.schemaFiles.addAll(files);

    // Ensure Schema files exist.
    if (this.schemaFiles.size() == 0) {
      logSettings(); // for easy debugging.
      throw new MojoExecutionException("No schemas found inside the <schemaDirectory> '"
          + this.getSchemaDirectory()
          + "'!");
    }

    // Setup binding files.
    if (!isDefined(getBindingDirectory(), 1)) {
      setBindingDirectory(getSchemaDirectory());
      if (isVerbose())
        getLog().info(
            "The <bindingDirectory> setting was not defined, assuming the same as <schemaDirectory>: "
                + getSchemaDirectory()
                + "");
    }

    files = gatherFiles(this.getBindingDirectory(), this.getBindingIncludes(), this
        .getBindingExcludes());

    it = files.iterator();
    for (; it.hasNext();)
      xjcOpts.addBindFile(getInputSource((File) it.next()));

    this.bindingFiles.addAll(files);

    // Setup Catalog files (XML Entity Resolver).
    if (isDefined(this.getCatalog(), 1)) {
      try {
        xjcOpts.addCatalog(this.getCatalog());
      }
      catch (IOException ex) {
        logSettings(); // for easy debugging.
        throw new MojoExecutionException("Error while setting the <catalog> to '"
            + this.getCatalog()
            + "'!", ex);
      }
    }

    // Setup Other Options

    xjcOpts.defaultPackage = this.getGeneratePackage();
    xjcOpts.targetDir = this.getGenerateDirectory();

    xjcOpts.strictCheck = this.isStrict();
    xjcOpts.readOnly = this.isReadOnly();

    if (this.isExtension())
      xjcOpts.compatibilityMode = Options.EXTENSION;

    setupCmdLineArgs(xjcOpts);

    return xjcOpts;
  }

  protected void setupCmdLineArgs(Options xjcOpts) throws MojoExecutionException {
    if (isDefined(this.getArgs(), 1)) {
      try {
        xjcOpts.parseArguments((String[]) getArgs().toArray(new String[getArgs().size()]));
      }
      catch (BadCommandLineException ex) {
        throw new MojoExecutionException("Error while setting CmdLine <args> options '"
            + this.getArgs()
            + "'!", ex);
      }
    }
  }

  /**
   * Sets up the verbose and debug mode depending on mvn logging level,
   * and sets up hyperjaxb logging.
   */
  protected void setupLogging() {
    Log log = getLog();

    // Maven's logging level should propagate to 
    // plugin's verbose/debug mode.
    if (log.isDebugEnabled())
      this.setDebug(true);

    if (this.isDebug()) {
      // If not verbose, debug messages would be lost.
      this.setVerbose(true);

      // Also print XJC add-on plugins instanciation messages.
      // NOTE that it must happend BEFORE 'Options' constructor. 
      System.setProperty("com.sun.tools.xjc.Options.findServices", "true");
    }
  }

  /**
   * Log the configuration settings. Shown when exception thrown or when
   * verbose is true.
   */
  protected void logSettings() {
    StringBuffer sb = new StringBuffer();

    sb.append("Plugin's mojo parameters: ");
    logUserSettings(sb);

    sb.append("\nCalculated options: ");
    logCalcSettings(sb);

    getLog().info(sb);
  }

  /**
   * Logs options defined directly as mojo parameters.
   */
  protected void logUserSettings(StringBuffer sb) {
    sb.append("\n\tschemaLanguage: " + getSchemaLanguage());
    sb.append("\n\tschemaDirectory: " + getSchemaDirectory());
    sb.append("\n\tincludeSchemas: " + recursiveToString(getSchemaIncludes()));
    sb.append("\n\texcludeSchemas: " + recursiveToString(getSchemasExcludes()));
    sb.append("\n\tbindingDirectory: " + getBindingDirectory());
    sb.append("\n\tincludeBindings: " + recursiveToString(getBindingIncludes()));
    sb.append("\n\texcludeBindings: " + recursiveToString(getBindingExcludes()));
    sb.append("\n\tdisableDefaultExcludes: " + isDisableDefaultExcludes());
    sb.append("\n\tcatalog: " + getCatalog());
    sb.append("\n\tdefaultPackage: " + getGeneratePackage());
    sb.append("\n\tdestinationDirectory: " + getGenerateDirectory());
    sb.append("\n\tforceRegenerate: " + isForceRegenerate());
    sb.append("\n\totherDepends: " + recursiveToString(getOtherDepends()));
    sb.append("\n\tremoveOldOutput: " + isRemoveOldOutput());
    sb.append("\n\twriteCode: " + isWriteCode());
    sb.append("\n\treadOnly: " + isReadOnly());
    sb.append("\n\textension: " + isExtension());
    sb.append("\n\tstrict: " + isStrict());
    sb.append("\n\tverbose: " + isVerbose());
    sb.append("\n\tdebug: " + isDebug());
    sb.append("\n\txjcArgs: " + recursiveToString(getArgs()));
  }

  /**
   * Logs options calculated by mojo parameters.
   */
  protected void logCalcSettings(StringBuffer sb) {
    sb.append("\n\tSchema File(s): " + recursiveToString(schemaFiles));
    sb.append("\n\tBinding File(s): " + recursiveToString(bindingFiles));
    sb.append("\n\tClassPath: " + recursiveToString(getClasspathElements()));
  }

  /**
   * @return true to indicate results are up-to-date, that is, when the latest 
   * 			from input files is earlier than the younger from the output 
   * 			files (meaning no re-execution required).
   */
  protected boolean isUpdToDate() throws MojoExecutionException {
    List dependsFiles = new ArrayList();
    List producesFiles = new ArrayList();

    gatherDependsFiles(dependsFiles);
    if (isVerbose() && !isDebug()) // If debug, they are printed along with modifTime.
      getLog().info("Checking up-to-date depends: " + recursiveToString(dependsFiles));

    gatherProducesFiles(producesFiles);
    if (isVerbose() && !isDebug()) // If debug, they are printed along with modifTime. 
      getLog().info("Checking up-to-date produces: " + recursiveToString(producesFiles));
    //////////////////
    // Perform the timestamp comparison.
    //////////////////

    // The older timeStamp of all input files;
    long inputTimeStamp = findLastModified(dependsFiles, true);

    // The younger of all destination files.
    long destTimeStamp = findLastModified(producesFiles, false);

    if (isVerbose())
      getLog().info(
          "Depends timeStamp: " + inputTimeStamp + ", produces timestamp: " + destTimeStamp);

    return inputTimeStamp < destTimeStamp;
  }

  protected void gatherDependsFiles(List inputFiles) {
    inputFiles.addAll(this.schemaFiles);
    inputFiles.addAll(this.bindingFiles);
    if (this.getCatalog() != null)
      inputFiles.add(this.getCatalog());

    // Pom dependency - typically when configuration settings change.
    if (getProject() != null) { // null when run for testing.
      inputFiles.add(getProject().getFile());
    }

    // Gather user-defined files and URLs.
    if (this.getOtherDepends() != null)
      inputFiles.addAll(Arrays.asList(this.getOtherDepends()));
  }

  protected void gatherProducesFiles(List destFiles) throws MojoExecutionException {
    if (this.getGenerateDirectory().exists()) {
      if (!this.getGenerateDirectory().isDirectory())
        getLog().warn(
            "The <generateDirectory>='"
                + getGenerateDirectory()
                + "' is not a directory!  Probably XJC will fail...");

      else
        destFiles.addAll(gatherFiles(this.getGenerateDirectory(), new String[]{
            "**/*.java",
            "**/bgm.ser",
            "**/jaxb.properties" }, null));
    }
  }

  /**
   * @param parent the returned classLoader will be a descendant of this one.
   * @return a context class loader with a classPath containing 
   * 			the project dependencies.
   */
  private ClassLoader getProjectDepsClassLoader(ClassLoader parent) {
    List urls = new ArrayList();
    if (this.getClasspathElements() != null) // For instance, when run for testing.
      for (Iterator it = this.getClasspathElements().iterator(); it.hasNext();) {
        String pathElem = (String) it.next();
        try {
          urls.add(new File(pathElem).toURL());
        }
        catch (MalformedURLException e) {
          getLog().warn(
              "Internal classpath element '"
                  + pathElem
                  + "' is was skiped due to: "
                  + getAllExMsgs(e, true));
        }
      }

    ClassLoader cl = new XJC2MojoClassLoader((URL[]) urls.toArray(new URL[urls.size()]), parent);
    return cl;
  }

  /**
   * 
   * @param xjcOpts
   * @throws MojoExecutionException
   */
  protected void runXJC(Options xjcOpts) throws MojoExecutionException {
    JaxbErrorReceiver4Mvn errorReceiver = new JaxbErrorReceiver4Mvn();

    Model model;
    if (isVerbose())
      getLog().info("Parsing input schema(s)...");

    errorReceiver.stage = "parsing";
    model = ModelLoader.load(xjcOpts, new JCodeModel(), errorReceiver);

    if (model == null)
      throw new MojoExecutionException(
          "Unable to parse input schema(s).  Error messages should have been provided.");

    try {

      if (isVerbose())
        getLog().info("Compiling input schema(s)...");

      errorReceiver.stage = "compiling";

      {
        final Outline outline = model.generateCode(xjcOpts, errorReceiver);
        if (outline == null) {
          throw new MojoExecutionException(
              "Failed to compile input schema(s)!  Error messages should have been provided.");
        }
      }
      //            if(Driver.generateCode( model, xjcOpts, errorReceiver )==null)
      //                throw new MojoExecutionException("Failed to compile input schema(s)!  Error messages should have been provided.");

      if (isWriteCode()) {
        if (isVerbose())
          getLog().info("Writing output to: " + xjcOpts.targetDir);

        model.codeModel.build(new JaxbCodeWriter4Mvn(xjcOpts.createCodeWriter()));
      }
      else {
        if (isVerbose())
          getLog().info("Code will not be written.");
      }
    }
    catch (IOException e) {
      throw new MojoExecutionException("Unable to write files: " + e.getMessage(), e);
    }
  }

  /**
   * Updates compilePath ans resources.
   *  
   * @throws MojoExecutionException
   */
  protected void updateMavenPaths() {
    // Mark XJC_generated java files for compilation.
    if (getProject() != null) // null when run for testing.
      getProject().addCompileSourceRoot(getGenerateDirectory().getPath());

    // Mark XJC_gen prop files as resources for inclusion to final jar.
    Resource jaxbRes = new Resource();
    jaxbRes.setDirectory(getGenerateDirectory().getPath());
    jaxbRes.addInclude("**/jaxb.properties");
    jaxbRes.addInclude("**/bgm.ser");

    if (getProject() != null) // null when run for testing.
      getProject().addResource(jaxbRes);
  }

  /**
   * 
   * @param files the fileNames or URLs to scan their lastModified timestamp.
   * @param oldest if true, returns the latest modificationDate of all files,
   * 		otherwise returns the earliest.
   * @return the older or younger last modification timestamp of all files.
   */
  protected long findLastModified(List/*<Object>*/files, boolean oldest) {
    long timeStamp = (oldest ? Long.MIN_VALUE : Long.MAX_VALUE);
    for (Iterator it = files.iterator(); it.hasNext();) {
      Object no = it.next();

      if (no != null) {
        long fileModifTime;
        if (no instanceof String) {// either URL or file
          String sdep = (String) no;

          try {
            URL url = new URL(sdep);

            URLConnection uCon = url.openConnection();
            uCon.setUseCaches(false);
            ////uCon.setConnectTimeout(5000);// JDK1.5 only!!!

            fileModifTime = uCon.getLastModified();

          }
          catch (MalformedURLException e) {
            fileModifTime = new File(sdep).lastModified();

          }
          catch (IOException ex) {
            fileModifTime = (oldest ? Long.MIN_VALUE : Long.MAX_VALUE);
            getLog().warn(
                "Skipping URL '"
                    + no
                    + "' from up-to-date check due to error while opening connection: "
                    + getAllExMsgs(ex, true));
          }

        }
        else
          // asume instanceof File
          fileModifTime = ((File) no).lastModified();

        if (isDebug())
          getLog().info((oldest ? "Depends " : "Produces ") + no + ": " + new Date(fileModifTime));

        if ((fileModifTime > timeStamp) ^ !oldest)
          timeStamp = fileModifTime;
      } // end if file null.
    }// end filesloop

    if (timeStamp == Long.MIN_VALUE) // no older file found
      return Long.MAX_VALUE; // assume re-execution required. 
    else if (timeStamp == Long.MAX_VALUE) // no younger file found
      return Long.MIN_VALUE; // assume re-execution required.

    return timeStamp;
  }

  /**
   * Converts a File object to an InputSource.
   */
  protected static InputSource getInputSource(File f) {
    try {
      return new InputSource(f.toURL().toExternalForm());
    }
    catch (MalformedURLException e) {
      return new InputSource(f.getPath());
    }
  }

  /**
   * 
   * @param baseDir
   * @param includesPattern
   * @param excludesPattern
   * @return all found absolute files (dirs and files).
   */
  protected List/*<File>*/gatherFiles(
      File baseDir,
      String[] includesPattern,
      String[] excludesPattern) throws MojoExecutionException {
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir(baseDir.getAbsoluteFile());
    scanner.setIncludes(includesPattern);
    scanner.setExcludes(getExcludes(excludesPattern));

    scanner.scan();

    List files = new ArrayList();
    String[] incFiles = scanner.getIncludedFiles();
    for (int i = 0; i < incFiles.length; i++) {
      String name = incFiles[i];
      try {
        File file = new File(baseDir, name);
        files.add(file.getCanonicalFile());
      }
      catch (IOException ex) {
        throw new MojoExecutionException("Unable to canonize the file [" + name + "]");
      }
    }

    return files;
  }

  protected String[] getExcludes(String[] origExcludes) {
    if (origExcludes == null)
      return null;
    List newExc = getExcludes(Arrays.asList(origExcludes));
    return (String[]) newExc.toArray(new String[newExc.size()]);
  }

  /**
   * Modifies input list by adding plexus tools default excludes.
   * 
   * @param origExcludes a list that must support the 'add' operation.
   * @return the augmented list or the input unchanged.
   */
  protected List/*<String>*/getExcludes(List/*<String>*/origExcludes) {
    if (origExcludes == null || this.isDisableDefaultExcludes())
      return origExcludes;

    origExcludes.addAll(Arrays.asList(FileUtils.getDefaultExcludes()));

    return origExcludes;
  }

  /**
   * A generic setting validator. Check for null and zero length of strings,
   * arrays and collections.
   * 
   * @param setting
   *            the settings to validate
   * @param minimumLength
   *            minimum length required.
   * @return true if setting is not null and has length or more items.
   */
  protected static boolean isDefined(Object setting, int minimumLength) {
    boolean defined = setting != null;

    if (setting instanceof Object[]) {
      defined = defined && ((Object[]) setting).length >= minimumLength;
    }
    else if (setting instanceof Collection) {
      defined = defined && ((Collection) setting).size() >= minimumLength;
    }
    else {
      defined = defined && setting.toString().trim().length() >= minimumLength;
    }

    return defined;
  }

  /**
   * A generic approach to turning the values inside arrays and collections
   * into toString values.
   * 
   * @param setting
   * @return complete toString values for most contained objects.
   */
  protected static String recursiveToString(Object setting) {
    if (setting == null)
      return "null";

    if (setting instanceof Collection) {
      Collection collection = (Collection) setting;
      setting = collection.toArray();
    }

    if (setting instanceof Object[]) {
      Object[] settingArray = (Object[]) setting;
      if (settingArray.length == 0)
        return "[]";

      StringBuffer result = new StringBuffer();

      result.append('[');
      result.append(recursiveToString(settingArray[0]));
      for (int index = 1; index < settingArray.length; index++) {
        result.append(", ");
        result.append(recursiveToString(settingArray[index]));
      }
      result.append(']');

      return result.toString();
    }

    return setting.toString();
  }

  public static String getAllExMsgs(Throwable ex, boolean includeExName) {
    StringBuffer sb = new StringBuffer((includeExName ? ex.toString() : ex.getLocalizedMessage()));
    
    Throwable cause = ex.getCause();
    Exception embeded = ex instanceof SAXParseException? ( (SAXParseException)ex).getException(): null;
    // Fisrt check that embeded and cause are the same,
    // then process each one.
    if ((cause == embeded && cause != null) || cause != null) 
            getAllCauseExMsgs(cause, includeExName, sb);
    else if (embeded != null)
            getAllCauseExMsgs(embeded, includeExName, sb);

    return sb.toString();
  }

  private static void getAllCauseExMsgs(Throwable ex, boolean includeExName, StringBuffer sb) {
      do {
          sb.append("\nCaused by: " + (includeExName? ex.toString(): ex.getLocalizedMessage()));
      } while ((ex = ex.getCause()) != null);
  }

  public static String getAllExStackTraces(Throwable ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    ex.printStackTrace(pw);

    Throwable cause = ex.getCause();
    Exception embeded = ex instanceof SAXParseException? ( (SAXParseException)ex).getException(): null;
    
    if (embeded != null && cause != embeded) {
      pw.append("Embeded ex:");
      embeded.printStackTrace(pw);
    }

    return sw.toString();
  }

//  private static void getAllCauseExStackTraces(Throwable ex, boolean includeExName, StringWriter sw) {
//      do {
//          sb.append("\nCaused by: " + (includeExName? ex.toString(): ex.getLocalizedMessage()));
//      } while ((ex = ex.getCause()) != null);
//  }

  public abstract void setSchemaLanguage(String schemaLanguage);

  public abstract String getSchemaLanguage();

  public abstract void setSchemaDirectory(File schemaDirectory);

  public abstract File getSchemaDirectory();

  public abstract void setSchemaIncludes(String[] schemaIncludes);

  public abstract String[] getSchemaIncludes();

  public abstract void setSchemasExcludes(String[] schemasExcludes);

  public abstract String[] getSchemasExcludes();

  public abstract void setBindingDirectory(File bindingDirectory);

  public abstract File getBindingDirectory();

  public abstract void setBindingIncludes(String[] bindingIncludes);

  public abstract String[] getBindingIncludes();

  public abstract void setBindingExcludes(String[] bindingExcludes);

  public abstract String[] getBindingExcludes();

  public abstract void setDisableDefaultExcludes(boolean disableDefaultExcludes);

  public abstract boolean isDisableDefaultExcludes();

  public abstract void setCatalog(File catalog);

  public abstract File getCatalog();

  public abstract void setGeneratePackage(String generatePackage);

  public abstract String getGeneratePackage();

  public abstract void setGenerateDirectory(File generateDirectory);

  public abstract File getGenerateDirectory();

  public abstract void setReadOnly(boolean readOnly);

  public abstract boolean isReadOnly();

  public abstract void setExtension(boolean extension);

  public abstract boolean isExtension();

  public abstract void setStrict(boolean strict);

  public abstract boolean isStrict();

  public abstract void setWriteCode(boolean writeCode);

  public abstract boolean isWriteCode();

  public abstract void setVerbose(boolean verbose);

  public abstract boolean isVerbose();

  public abstract void setDebug(boolean debug);

  public abstract boolean isDebug();

  public abstract void setArgs(List args);

  public abstract List getArgs();

  public abstract void setForceRegenerate(boolean forceRegenerate);

  public abstract boolean isForceRegenerate();

  public abstract void setRemoveOldOutput(boolean removeOldOutput);

  public abstract boolean isRemoveOldOutput();

  public abstract void setOtherDepends(String[] otherDepends);

  public abstract String[] getOtherDepends();

  public abstract List getClasspathElements();

  public abstract MavenProject getProject();

  protected class JaxbErrorReceiver4Mvn extends ErrorReceiver {

    public String stage = "processing";

    public void warning(SAXParseException e) {
      getLog().warn(makeMessage(e, true));
    }

    public void error(SAXParseException e) {
      getLog().error(makeMessage(e, true));
    }

    public void fatalError(SAXParseException e) {
      getLog().error(makeMessage(e, true));
    }

    public void info(SAXParseException e) {
      if (isVerbose())
        getLog().info(makeMessage(e, false));
    }

    private String makeMessage(SAXParseException ex, boolean printExName) {
      int row = ex.getLineNumber();
      int col = ex.getColumnNumber();
      String sys = ex.getSystemId();
      String pub = ex.getPublicId();

      String exString;
      if (isDebug()) {
        exString = getAllExStackTraces(ex);
      }
      else
        exString = getAllExMsgs(ex, printExName);

      return "XJC while "
          + stage
          + " schema(s)"
          + (sys != null ? " " + sys : "")
          + (pub != null ? " " + pub : "")
          + (row > 0 ? "[" + row + (col > 0 ? "," + col : "") + "]" : "")
          + ": "
          + exString;
    }
  }

  private class JaxbCodeWriter4Mvn extends CodeWriter {
    private final CodeWriter output;

    public JaxbCodeWriter4Mvn(CodeWriter output) {
      this.output = output;
    }

    public Writer openSource(JPackage pkg, String fileName) throws IOException {
      if (isVerbose()) {
        if (pkg.isUnnamed())
          getLog().info("XJC writing: " + fileName);
        else
          getLog().info(
              "XJC writing: "
                  + pkg.name().replace('.', File.separatorChar)
                  + File.separatorChar
                  + fileName);
      }

      return output.openSource(pkg, fileName);
    }

    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
      if (isVerbose()) {
        if (pkg.isUnnamed())
          getLog().info("XJC writing: " + fileName);
        else
          getLog().info(
              "XJC writing: "
                  + pkg.name().replace('.', File.separatorChar)
                  + File.separatorChar
                  + fileName);
      }

      return output.openBinary(pkg, fileName);
    }

    public void close() throws IOException {
      output.close();
    }

  }
}
