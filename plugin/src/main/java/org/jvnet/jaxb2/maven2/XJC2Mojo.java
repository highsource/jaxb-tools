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
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * A mojo that uses JAXB 1.x XJC compiler to generate java source classes from
 * schemas (XML schemas, DTD, WSDL, or RELAXNG). For details on JAXB see <a
 * href="https://jaxb.dev.java.net/1.0/">JAXB 1.x Project</a>.
 * 
 * @goal generate
 * @phase generate-sources
 * 
 * @description JAXB-1.x Generator Plugin for Maven-2, bound by default at
 *              'generate-sources' phase.
 * @author Kostis Anagnostopoulos (ankostis@mail.com)
 */
public class XJC2Mojo extends AbstractXJC2Mojo {

	/**
	 * <p>Type of input schema language. One of:
	 * 		 DTD, XMLSCHEMA, RELAXNG, RELAXNG_COMPACT, WSDL, AUTODETECT  
	 * </p>
	 * <p>If unspecified, it is assumed AUTODETECT.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.schemaLanguage}"
	 * 
	 */
	protected String schemaLanguage;
	
	/**
	 * The source directory containing *.xsd schema files. Notice that 
	 * binding files are searched by default in this deriectory.
	 * 
	 * @parameter default-value="src/main/resources" expression="${maven.xjc2.schemaDirectory}"
	 * @required
	 */
	protected File schemaDirectory;

	/**
	 * <p>A list of regular expression file search patterns to specify the schemas
	 * to be processed. Searching is based from the root of <code>schemaDirectory</code>. 
	 * </p>
	 * <p>If left udefined, then all *.xsd files in schemaDirectory will be processed.
	 * </p>
	 * 
     * @parameter
	 */
	protected String[] schemaIncludes = new String[]{"*.xsd"};

	/**
	 * A list of regular expression file search patterns to specify the schemas
	 * to be excluded from the <code>schemaIncludes</code> list. 
	 * Searching is based from the root of schemaDirectory.
	 * 
	 * @parameter
	 */
	protected String[] schemasExcludes;

	/**
	 * <p>The source directory containing the *.xjb binding files.  
	 * </p>
	 * <p>If left undefined, then the <code>schemaDirectory</code> is assumed.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.bindingDirectory}"
	 */
	protected File bindingDirectory;

	/**
	 * <p>A list of regular expression file search patterns to specify the binding
	 * files to be processed. Searching is based from the root of
	 * <code>bindingDirectory</code>. 
	 * </p>
	 * <p>If left undefined, then all *.xjb files in schemaDirectory will be 
	 * processed.
	 * </p>
	 * 
     * @parameter
	 */
	protected String[] bindingIncludes = new String[]{"*.xjb"};

	/**
	 * A list of regular expression file search patterns to specify the binding
	 * files to be excluded from the <code>bindingIncludes</code>. 
	 * Searching is based from the root of bindingDirectory.
	 * 
	 * @parameter
	 */
	protected String[] bindingExcludes;

    /**
     * If 'true', maven's default exludes are NOT added to all 
     * the excludes lists.
     * 
     * @parameter default-value="false" expression="${maven.xjc2.disableDefaultExcludes}"
     */
    protected boolean disableDefaultExcludes;
    
	/**
	 * <p>Specify the catalog file to resolve external entity references 
	 * (xjc's -catalog option) 
	 * </p>
	 * <p>Support TR9401, XCatalog, and OASIS XML Catalog format. See 
	 * the catalog-resolver sample and this article for details.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.catalog}"
	 */
	protected File catalog;

	/**
	 * <p>The generated classes will all be placed under this Java package 
	 * (xjc's -p option), unless otherwise specified in the schemas.  
	 * </p>
	 * <p>If left unspecified, the package will be derived from the schemas only.
	 * </p>
	 * 
	 * @parameter expression="${maven.xjc2.generatePackage}"
	 */
	protected String generatePackage;

	/**
	 * <p>Generated code will be written under this directory. 
	 * </p>
	 * <p>For instance, if you specify <code>generateDirectory="doe/ray"</code>
	 *  and <code>generatePackage="org.here"</code>, then files are generated 
	 *  to <code>doe/ray/org/here</code>.
	 * </p>
	 * 
	 * @parameter default-value="${project.build.directory}/generated-sources/xjc"  expression="${maven.xjc2.generateDirectory}"
	 * @required
	 */
	protected File generateDirectory;

	/**
	 * If 'true', the generated Java source files are set as read-only  
	 * (xjc's -readOnly option).
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.readOnly}"
	 */
	protected boolean readOnly;

	/**
	 * If 'true', the XJC binding compiler will run in the extension mode
	 * (xjc's -extension option).
	 * Otherwise, it will run in the strict conformance mode. 
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.extension}"
	 */
	protected boolean extension;

	/**
	 * If 'true', Perform strict validation of the input schema (xjc's -nv option).
	 * 
	 * @parameter default-value="true" expression="${maven.xjc2.strict}"
	 */
	protected boolean strict;

    /** 
     * If 'true', equivalent to specifying the '-trace-unmarshaller' option to XJC. 
     * 
	 * @parameter default-value="false" expression="${maven.xjc2.traceUnmarshaller}"
     */
	protected boolean traceUnmarshaller;
    
    /**
     * If 'false', the plugin will not write the generated code to disk.
     * 
     * @parameter default-value="true" expression="${maven.xjc2.writeCode}"
     */
    protected boolean writeCode = true;
    
    /** 
     * If 'false', XJC will not generate code for the on-demand validation. 
     * 
	 * @parameter default-value="true" expression="${maven.xjc2.generateValidationCode}"
     */
	protected boolean generateValidationCode;
    
    /** 
     * If 'false', XJC will not generate code for the marshalling.
     *  
	 * @parameter default-value="true" expression="${maven.xjc2.generateMarshallingCode}"
     */
	protected boolean generateMarshallingCode;
    
    /** 
     * If 'false', XJC will not generate code for the unmarshalling. 
     *  
	 * @parameter default-value="true" expression="${maven.xjc2.generateUnmarshallingCode}"
     */
	protected boolean generateUnmarshallingCode;

    /**
     * <p>The package name of the generated runtime. Leave unspecified
     * to generate it into the default location.  If specified,
     * it avoids generating the runtime.
     * </p>
     * <p>This option is useful to consolidate the runtimes into one 
     * when a lot of schemas are compiled separately.
     * </p>
     * 
	 * @parameter expression="${maven.xjc2.runtimePackage}"
     */
	protected String runtimePackage;

///////////////////////////////////////////////
// NO, set java VM proxy properties into .m2/settings.xml instead.
///////////////////////////////////////////////
//    /**
//     * Set VM's HTTP/HTTPS proxy properties. 
//     * 
//     * @parameter
//     */
//	protected String proxyHost;
//
//    /**
//     * Set VM's HTTP/HTTPS proxy properties. 
//     * 
//     * @parameter
//     */
//	protected String proxyPort;
///////////////////////////////////////////////
	
    /**
	 * <p>If 'true', the plugin and the XJC compiler are both set to verbose mode
	 * (xjc's -verbose option).  
	 * </p>
	 * <p>It is automatically set to 'true' when maven is run in debug mode 
	 * (mvn's -X option).
	 * </p>
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.verbose}"
	 */
	protected boolean verbose;

	/**
	 * <p>If 'true', the XJC compiler is set to debug mode
	 * (xjc's -debug option)and the 'com.sun.tools.xjc.Options.findServices' 
	 * property is set, to print any add-on instanciation messages.
	 * </p>
	 * <p>It is automatically set to 'true' when maven is run in debug mode 
	 * (mvn's -X option).
	 * </p>
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.debug}"
	 */
	protected boolean debug;

	/**
	 * <p>A list of extra XJC's command-line arguments
	 * (items must include the dash '-').
	 * </p>
	 * <p>Arguments set here take precedence over other mojo parameters.
	 * </p>
	 * 
	 * @parameter
	 */
	protected List args;

	/**
	 * If 'true', no up-to-date check is performed and the XJC always
	 * re-generates the sources.
	 * 
	 * @parameter default-value="false" expression="${maven.xjc2.forceRegenerate}"
	 */
	protected boolean forceRegenerate;
	
	/**
	 * <p>If 'true', the [generateDirectory] dir will be deleted before 
	 * the XJC binding compiler recompiles the source files.
	 * </p>
	 * <p>Note that if set to 'false', the up-to-date check might not work,
	 * since XJC does not regenerate all files (i.e. files for "any" 
	 * elements under 'xjc/org/w3/_2001/xmlschema' directory).
	 * </p>
	 * 
	 * @parameter default-value="true" expression="${maven.xjc2.removeOldOutput}"
	 */
	protected boolean removeOldOutput;

	/**
	 * A list of of input files or URLs to consider during the up-to-date.  
	 * By default it always considers: 1. schema files, 2. binding files, 
	 * 3. catalog file, and 4. the pom.xml file of the project executing this plugin. 
	 * 
	 * @parameter
	 */
	protected String[] otherDepends;
	
    /**
     * Project classpath.  Used internally when runing the XJC compiler.
     * 
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
	protected List classpathElements;

	/**
	 * The Maven project reference, used internally.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;
	
	/**
	 * Execute the maven2 mojo to invoke the xjc2 compiler based on any
	 * configuration settings.
	 * 
	 * @throws MojoExecutionException
	 */
	public void execute() throws MojoExecutionException {
		executeImp();
	}

	protected void setSchemaLanguage(String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	protected String getSchemaLanguage() {
		return schemaLanguage;
	}

	protected void setSchemaDirectory(File schemaDirectory) {
		this.schemaDirectory = schemaDirectory;
	}

	protected File getSchemaDirectory() {
		return schemaDirectory;
	}

	protected void setSchemaIncludes(String[] schemaIncludes) {
		this.schemaIncludes = schemaIncludes;
	}

	protected String[] getSchemaIncludes() {
		return schemaIncludes;
	}

	protected void setSchemasExcludes(String[] schemasExcludes) {
		this.schemasExcludes = schemasExcludes;
	}

	protected String[] getSchemasExcludes() {
		return schemasExcludes;
	}

	protected void setBindingDirectory(File bindingDirectory) {
		this.bindingDirectory = bindingDirectory;
	}

	protected File getBindingDirectory() {
		return bindingDirectory;
	}

	protected void setBindingIncludes(String[] bindingIncludes) {
		this.bindingIncludes = bindingIncludes;
	}

	protected String[] getBindingIncludes() {
		return bindingIncludes;
	}

	protected void setBindingExcludes(String[] bindingExcludes) {
		this.bindingExcludes = bindingExcludes;
	}

	protected String[] getBindingExcludes() {
		return bindingExcludes;
	}

	protected void setDisableDefaultExcludes(boolean disableDefaultExcludes) {
		this.disableDefaultExcludes = disableDefaultExcludes;
	}

	protected boolean isDisableDefaultExcludes() {
		return disableDefaultExcludes;
	}

	protected void setCatalog(File catalog) {
		this.catalog = catalog;
	}

	protected File getCatalog() {
		return catalog;
	}

	protected void setGeneratePackage(String generatePackage) {
		this.generatePackage = generatePackage;
	}

	protected String getGeneratePackage() {
		return generatePackage;
	}

	protected void setGenerateDirectory(File generateDirectory) {
		this.generateDirectory = generateDirectory;
	}

	protected File getGenerateDirectory() {
		return generateDirectory;
	}

	protected void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	protected boolean isReadOnly() {
		return readOnly;
	}

	protected void setExtension(boolean extension) {
		this.extension = extension;
	}

	protected boolean isExtension() {
		return extension;
	}

	protected void setStrict(boolean strict) {
		this.strict = strict;
	}

	protected boolean isStrict() {
		return strict;
	}

	protected void setTraceUnmarshaller(boolean traceUnmarshaller) {
		this.traceUnmarshaller = traceUnmarshaller;
	}

	protected boolean isTraceUnmarshaller() {
		return traceUnmarshaller;
	}
    
    protected boolean isWriteCode() {
        return writeCode;
    }
    
    protected void setWriteCode(boolean writeCode) {
        this.writeCode = writeCode;
    }

	protected void setGenerateValidationCode(boolean generateValidationCode) {
		this.generateValidationCode = generateValidationCode;
	}

	protected boolean isGenerateValidationCode() {
		return generateValidationCode;
	}

	protected void setGenerateMarshallingCode(boolean generateMarshallingCode) {
		this.generateMarshallingCode = generateMarshallingCode;
	}

	protected boolean isGenerateMarshallingCode() {
		return generateMarshallingCode;
	}

	protected void setGenerateUnmarshallingCode(
			boolean generateUnmarshallingCode) {
		this.generateUnmarshallingCode = generateUnmarshallingCode;
	}

	protected boolean isGenerateUnmarshallingCode() {
		return generateUnmarshallingCode;
	}

	protected void setRuntimePackage(String runtimePackage) {
		this.runtimePackage = runtimePackage;
	}

	protected String getRuntimePackage() {
		return runtimePackage;
	}

	protected void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	protected boolean isVerbose() {
		return verbose;
	}

	protected void setDebug(boolean debug) {
		this.debug = debug;
	}

	protected boolean isDebug() {
		return debug;
	}

	protected void setArgs(List args) {
		this.args = args;
	}

	protected List getArgs() {
		return args;
	}

	protected void setForceRegenerate(boolean forceRegenerate) {
		this.forceRegenerate = forceRegenerate;
	}

	protected boolean isForceRegenerate() {
		return forceRegenerate;
	}

	protected void setRemoveOldOutput(boolean removeOldOutput) {
		this.removeOldOutput = removeOldOutput;
	}

	protected boolean isRemoveOldOutput() {
		return removeOldOutput;
	}

	protected void setOtherDepends(String[] otherDepends) {
		this.otherDepends = otherDepends;
	}

	protected String[] getOtherDepends() {
		return otherDepends;
	}

	protected void setClasspathElements(List classpathElements) {
		this.classpathElements = classpathElements;
	}

	protected List getClasspathElements() {
		return classpathElements;
	}

	protected void setProject(MavenProject project) {
		this.project = project;
	}

	protected MavenProject getProject() {
		return project;
	}

}
